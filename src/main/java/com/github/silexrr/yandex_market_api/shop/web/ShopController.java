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
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @PostMapping("/list")
    public String shopDelete( @RequestParam(value = "delete") Shop shop) {
        if (shop != null) {
            shopService.delete(shop);
        }
        return "redirect:/shop/list";
    }

    @GetMapping("/edit/{shop}")
    public String shopEdit(
            Model model,
            @PathVariable(value = "shop") Optional<Shop> shopOptional
    ) {
        if (shopOptional.isPresent()) {
            model.addAttribute("shop", shopOptional.get());
        } else {
            model.addAttribute("shop", new Shop());
        }
        return "shop/edit";
    }

    @PostMapping("/edit/{shop}")
    public String shopEditSave(
            @PathVariable(value = "shop") Optional<Shop> shopOptional,
            @ModelAttribute("shop") Shop shop,
            BindingResult bindingResult
    ){
        if (shopOptional.isPresent()) {
            shop.setId(shopOptional.get().getId());
        }
        if (shop.getYmCompanyId() == null) {
            shop.setYmCompanyId(0);
        }
        if (shop.getYmRegionId() == null) {
            shop.setYmRegionId(0);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null ) {
            User principal = (User)authentication.getPrincipal();
            shop.setUserOwner(principal);
        }

        shopValidator.validate(shop, bindingResult);
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error: allErrors) {
            System.out.println(error.getCode() + " " + error.getDefaultMessage());
        }
        if (bindingResult.hasErrors()) {
            return  "shop/edit/new";
        }

        shopService.save(shop);
        return "redirect:/shop/edit/" + shop.getId();
    }



//    @GetMapping("/edit/new")
//    public String addPrepare( Model model ) {
//        model.addAttribute("shop", new Shop());
//        return "shop/edit";
//    }
//
//    @PostMapping("/edit/new")
//    public String addExecute(
//            @ModelAttribute("shop") Shop shop,
//            BindingResult bindingResult
//    ) {
//
//
//    }
}
