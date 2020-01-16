package com.github.silexrr.yandex_market_api.shop.web;

import com.github.silexrr.yandex_market_api.auth.model.User;
import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.model.Token;
import com.github.silexrr.yandex_market_api.shop.repository.TokenRepository;
import com.github.silexrr.yandex_market_api.shop.service.ShopService;
import com.github.silexrr.yandex_market_api.shop.service.TokenService;

import com.github.silexrr.yandex_market_api.shop.service.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.naming.Binding;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = "/shop/{shop}/token")
public class TokenController {

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private TokenValidator tokenValidator;
    @Autowired
    private ShopService shopService;


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

    @PostMapping("/edit/{token}")
    public String edit(
            @PathVariable(value = "shop") Shop shop,
            @ModelAttribute(value = "token") Token token,
            BindingResult bindingResult
    ){

        if(!shopService.currentUserHasAccess(shop)) {
            return "redirect:/shop/list";
        }
        if (token.getShop() == null) {
            token.setShop(shop);
        }

        tokenValidator.validate(token, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/shop/" + shop.getId() + "/token/edit/new";
        }

        tokenService.save(token);

        return "redirect:/shop/" + shop.getId() + "/token/edit/" + token.getId();
    }

}
