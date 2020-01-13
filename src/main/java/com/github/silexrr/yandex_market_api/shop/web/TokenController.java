package com.github.silexrr.yandex_market_api.shop.web;

import com.github.silexrr.yandex_market_api.auth.model.User;
import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.model.Token;
import com.github.silexrr.yandex_market_api.shop.repository.TokenRepository;
import com.github.silexrr.yandex_market_api.shop.service.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/shop/{shop}/token")
public class TokenController {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private TokenService tokenService;


    @GetMapping("/list")
    public String list(
            Map<String, Object> model,
            @PathVariable(value = "shop") Shop shop
    ) {
        List<Token> tokens = tokenRepository.findByShop(shop);

        model.put("tokens", tokens);
        model.put("shop", shop);
        return "/shop/token/list";
    }

    @GetMapping("/edit/new")
    public String addPrepare(
            Model model,
            @PathVariable(value = "shop") Shop shop
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return "redirect:/";
        }
        User principal = (User) authentication.getPrincipal();
        if (!shop.getUserOwner().equals(principal)) {
            return "redirect:/shop/list";
        }
        model.addAttribute("shop", shop);
        model.addAttribute("token", new Token());
        return "/shop/token/edit";
    }

}
