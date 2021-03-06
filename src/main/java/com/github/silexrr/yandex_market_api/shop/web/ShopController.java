package com.github.silexrr.yandex_market_api.shop.web;

import com.github.silexrr.yandex_market_api.auth.model.User;
import com.github.silexrr.yandex_market_api.auth.web.JwtTokenUtil;
import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.service.ShopService;
import com.github.silexrr.yandex_market_api.shop.service.ShopValidator;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopValidator shopValidator;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/list")
    public String list(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Shop> shops = new ArrayList<>();
        if (authentication != null) {
            User principal = (User)authentication.getPrincipal();
            shops = shopService.findByUserOwnersContains(principal);
        }
        HashMap<String, Integer> shopsTokenCount = new HashMap<>();

        shops.forEach(shop -> shopsTokenCount.put(shop.getId(), shopService.countToken(shop)));

        model.addAttribute("shops", shops);
        model.addAttribute("shopsTokenCount", shopsTokenCount);

        return "shop/list";
    }

    @PostMapping("/list")
    public String shopDelete( @RequestParam(value = "delete") Shop shop) {
        User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (shop != null
            && shop.getUserOwners().contains(principal)
        ) {
            shopService.delete(shop);
        }
        return "redirect:/shop/list";
    }

    @GetMapping("/edit/{shop}")
    public String shopEdit(
            Model model,
            @PathVariable(value = "shop") Optional<Shop> shopOptional
    ) {
        Shop shop = null;
        if (shopOptional.isPresent()) {
            shop = shopOptional.get();
        }
        if (shop == null) {
            shop = new Shop();
        }
        model.addAttribute("shop", shop);
        return "shop/edit";
    }

    @PostMapping("/edit/{shop}")
    public String shopEditSave(
            @PathVariable(value = "shop") Optional<Shop> shopOptional,
            @ModelAttribute("shop") Shop shop,
            BindingResult bindingResult
    ){
        shopOptional.ifPresent(value -> shop.setId(value.getId()));
        if (shop.getYmCompanyId() == null) {
            shop.setYmCompanyId(0);
        }
        if (shop.getYmRegionId() == null) {
            shop.setYmRegionId(0);
        }

        List<User> userOwners = shop.getUserOwners();
        if(userOwners.size() == 0) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null ) {
                User principal = (User)authentication.getPrincipal();
                userOwners.add(principal);
                shop.setUserOwners(userOwners);
            }
        }

        shopValidator.validate(shop, bindingResult);
//        List<ObjectError> allErrors = bindingResult.getAllErrors();
//        for (ObjectError error: allErrors) {
//            System.out.println(error.getCode() + " " + error.getDefaultMessage());
//        }
        if (bindingResult.hasErrors()) {
            return  "shop/edit";
        }

        shopService.save(shop);
        return "redirect:/shop/edit/" + shop.getId();
    }

//
//    @PostMapping("/edit/{shop}")
//    public String shopEditSave(
//            @PathVariable(value = "shop") Optional<Shop> shopOptional,
//            @ModelAttribute("shop") Shop shop,
//            BindingResult bindingResult
//    ){
////        System.out.println(bindingResult);
//        if (shop.getYmCompanyId() == null) {
//            shop.setYmCompanyId(0);
//        }
//        if (shop.getYmRegionId() == null) {
//            shop.setYmRegionId(0);
//        }
//
//        List<User> userOwners = shop.getUserOwners();
//        if(userOwners.size() == 0) {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication != null ) {
//                User principal = (User)authentication.getPrincipal();
//                userOwners.add(principal);
//                shop.setUserOwners(userOwners);
//            }
//        }
//
//        shopValidator.validate(shop, bindingResult);
////        List<ObjectError> allErrors = bindingResult.getAllErrors();
////        for (ObjectError error: allErrors) {
////            System.out.println(error.getCode() + " " + error.getDefaultMessage());
////        }
//        if (bindingResult.hasErrors()) {
//            return  "shop/edit";
//        }
//
//        shopService.save(shop);
//        return "redirect:/shop/edit/" + shop.getId();
//    }



    @GetMapping("/edit/new")
    public String addPrepare( Model model ) {
        model.addAttribute("shop", new Shop());
        return "shop/edit";
    }
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
