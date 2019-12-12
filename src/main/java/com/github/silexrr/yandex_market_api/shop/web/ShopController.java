package com.github.silexrr.yandex_market_api.shop.web;

import com.github.silexrr.yandex_market_api.auth.model.User;
import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.repository.ShopRepository;
import com.github.silexrr.yandex_market_api.shop.service.ShopValidator;
import com.github.silexrr.yandex_market_api.shop.service.ShopService;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/shop")
public class ShopController {
    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopValidator shopValidator;

    @GetMapping("/list")
    public String list(Map<String, Object> model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Shop> shops = new ArrayList<Shop>();
        if (authentication != null) {
            User principal = (User)authentication.getPrincipal();
            shops = shopRepository.findByUserOwner(principal);
        }
        model.put("shops", shops);

        return "shop/list";
    }

    @GetMapping("/add")
    public String addPrepare( Model model ) {
        model.addAttribute("shopForm", new Shop());
        return "shop/add";
    }

    @PostMapping("/add")
    public String addExecute(
            @ModelAttribute("shopForm") Shop shopForm,
            BindingResult bindingResult
    ) {
        if (shopForm.getYmCompanyId() == null) {
            shopForm.setYmCompanyId(0);
        }
        if (shopForm.getYmRegionId() == null) {
            shopForm.setYmRegionId(0);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null ) {
            User principal = (User)authentication.getPrincipal();
            shopForm.setUserOwner(principal);
        }

        shopValidator.validate(shopForm, bindingResult);
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error: allErrors) {
            System.out.println(error.getCode() + " " + error.getDefaultMessage());
        }
        if (bindingResult.hasErrors()) {
            return  "shop/add";
        }


        shopService.save(shopForm);
        return "redirect:/shop/list";

    }
}
