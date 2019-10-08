package com.github.silexrr.yandex_market_api.auth.web;

import com.github.silexrr.yandex_market_api.auth.model.User;
import com.github.silexrr.yandex_market_api.auth.service.SecurityService;
import com.github.silexrr.yandex_market_api.auth.service.UserService;
import com.github.silexrr.yandex_market_api.auth.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

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
        System.out.println(userForm);
        System.out.println(bindingResult);
//        bindingResult
//        model.addAttribute("userForm", userForm);
//        model.addAttribute("bindingResult", bindingResult);
        if (bindingResult.hasErrors()) {
            return "auth/registration";
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

//    @GetMapping({"/", "/welcome"})
//    public String welcome(Model model) {
//        return "";
//    }
}