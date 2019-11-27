package com.github.silexrr.yandex_market_api.shop.web;

import com.github.silexrr.yandex_market_api.auth.model.User;
import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/shop")
public class ShopController {
    @Autowired
    private ShopRepository shopRepository;

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
        model.addAttribute("shop", new Shop());
        return "shop/add";
    }

    @PostMapping("/add")
    public String addExecute(
            @Valid Shop shop,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return  "shop/add";
        }
        return "shop/list";
    }
}
