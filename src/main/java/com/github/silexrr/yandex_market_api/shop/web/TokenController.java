package com.github.silexrr.yandex_market_api.shop.web;

import com.github.silexrr.yandex_market_api.auth.model.User;
import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.model.Token;
import com.github.silexrr.yandex_market_api.shop.service.ShopService;
import com.github.silexrr.yandex_market_api.shop.service.TokenService;

import com.github.silexrr.yandex_market_api.shop.service.TokenValidator;
import com.github.silexrr.yandex_market_api.yandexApi.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/shop/{shop}/token")
public class TokenController {

//    @Autowired
//    private TokenRepository tokenRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private TokenValidator tokenValidator;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ResponseService responseService;


    @GetMapping("/list")
    public String list(
            Map<String, Object> model,
            @PathVariable(value = "shop") Shop shop
    ) {
        model.put("shop", shop);
        return "/shop/token/list";
    }

    @PostMapping("/list")
    public String tokenDelete(
        @PathVariable(value = "shop") Shop shop,
        @RequestParam(value = "delete") String tokenId
    ) {
        User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (shop != null
            && shopService.userHasAccess(shop, principal)
        ) {
            Token token = shopService.findTokenById(shop, tokenId);
            if (token != null) {
                if (shopService.removeToken(shop, token)) {
                    shopService.save(shop);
                }
            }
        }
        return "redirect:/shop/" + shop.getId() + "/token/list";
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
        if (!shop.getUserOwners().contains(principal)) {
            return "redirect:/shop/list";
        }
        model.addAttribute("shop", shop);
        model.addAttribute("isNew", true);
        model.addAttribute("token", new Token());
        return "/shop/token/edit";
    }

    @PostMapping("/edit/{token}")
    public String edit(
            @PathVariable(value = "shop") Shop shop,
            @PathVariable(value = "token") String tokenId,
            @ModelAttribute(value = "token") Token token,
            BindingResult bindingResult
    ){

        if(!shopService.currentUserHasAccess(shop)) {
            return "redirect:/shop/list";
        }

//        Token token = shopService.findTokenById(shop, tokenId);
//        if (token == null) {
//            token = new Token();
////            return "redirect:/shop/" + shop.getId() + "/token/list";
//        }
//        System.out.println(token.getId());
        tokenValidator.validate(token, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/shop/token/edit";
        }
        List<Token> tokens = shop.getTokens();
        Token tokenExist = shopService.findTokenById(shop, tokenId);
        if (tokenExist != null) {
            tokens.remove(tokenExist);
            token.setId(tokenId);
        }
        tokens.add(token);
        shop.setTokens(tokens);
        shopService.save(shop);

        //tokenService.save(token);

        return "redirect:/shop/" + shop.getId() + "/token/edit/" + token.getId();
    }

    @GetMapping("/edit/{token}")
    public String show(
            Model model,
            @PathVariable(value = "shop") Shop shop,
            @PathVariable(value = "token") String tokenId
    ) {
        Token token = shopService.findTokenById(shop, tokenId);
        if (token == null) {
            return "redirect:/shop/" + shop.getId() + "/token/list";
        }

//        UUID uuid = UUID.randomUUID();

//        Request request = new Request(uuid.toString());
//        request.setShop(shop);
//        request.setToken(token);
//        Campaigns campaigns = new Campaigns(request);
//        RequestService requestService = new RequestService();
//        String response = requestService.send(campaigns);
//
//        Response response1 = new Response(request, response, campaigns.getMethodName());
//        responseService.save(response1);
//
//        System.out.println(response);

        model.addAttribute("token", token);
        model.addAttribute("shop", shop);
        return "/shop/token/edit";
    }

}
