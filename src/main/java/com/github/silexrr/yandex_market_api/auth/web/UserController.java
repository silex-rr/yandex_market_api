package com.github.silexrr.yandex_market_api.auth.web;

import com.github.silexrr.yandex_market_api.auth.model.Role;
import com.github.silexrr.yandex_market_api.auth.model.User;
import com.github.silexrr.yandex_market_api.auth.repository.UserRepository;
import com.github.silexrr.yandex_market_api.auth.service.SecurityService;
import com.github.silexrr.yandex_market_api.auth.service.UserService;
import com.github.silexrr.yandex_market_api.auth.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;


@Controller
@RequestMapping(value = "/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
//        System.out.println("userForm");
//        System.out.println("bindingResult");
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm,
                               BindingResult bindingResult
    ) {
        userValidator.validate(userForm, bindingResult);
//        bindingResult.errorM
//        System.out.println(userForm);
//        System.out.println(bindingResult);
//        bindingResult
//        model.addAttribute("userForm", userForm);
//        model.addAttribute("bindingResult", bindingResult);
//        System.out.println(bindingResult.hasErrors());
//        List<ObjectError> allErrors = bindingResult.getAllErrors();
//        for (ObjectError error: allErrors) {
////            System.out.println(error.getCode() + " " + error.getDefaultMessage());
//        }
        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }

        long count = userRepository.count();
//        System.out.println(count);
        if (count == (long) 0) {
            Set<Role> roles = userForm.getRoles();
            roles.add(Role.ADMIN);
            userForm.setRoles(roles);
            userForm.setActive(true);
        }
        userService.save(userForm);

        securityService.autoLogin(userForm.getLogin(), userForm.getPasswordConfirm());

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "auth/login";
    }

//    @PostMapping("/logout")
//    public String logout() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//
//        }
//        return "redirect:/";
//    }

//    @GetMapping({"/", "/welcome"})
//    public String welcome(Model model) {
//        return "";
//    }
}
