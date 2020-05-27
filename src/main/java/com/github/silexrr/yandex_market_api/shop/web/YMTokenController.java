package com.github.silexrr.yandex_market_api.shop.web;

import com.github.silexrr.yandex_market_api.auth.model.User;
import com.github.silexrr.yandex_market_api.shop.model.Shop;
import com.github.silexrr.yandex_market_api.shop.model.YMToken;
import com.github.silexrr.yandex_market_api.shop.service.ShopService;
import com.github.silexrr.yandex_market_api.shop.service.YMTokenService;

import com.github.silexrr.yandex_market_api.shop.service.YMTokenValidator;
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
@RequestMapping(value = "/shop/{shop}/YMToken")
public class YMTokenController {

//    @Autowired
//    private TokenRepository tokenRepository;
    @Autowired
    private YMTokenService tokenService;
    @Autowired
    private YMTokenValidator tokenValidator;
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
        return "/shop/YMToken/list";
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
            YMToken YMToken = shopService.findTokenById(shop, tokenId);
            if (YMToken != null) {
                if (shopService.removeToken(shop, YMToken)) {
                    shopService.save(shop);
                }
            }
        }
        return "redirect:/shop/" + shop.getId() + "/YMToken/list";
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
        model.addAttribute("YMToken", new YMToken());
        return "/shop/YMToken/edit";
    }

    @PostMapping("/edit/{YMToken}")
    public String edit(
            @PathVariable(value = "shop") Shop shop,
            @PathVariable(value = "YMToken") String tokenId,
            @ModelAttribute(value = "YMToken") YMToken YMToken,
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
        tokenValidator.validate(YMToken, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/shop/YMToken/edit";
        }
        List<YMToken> YMTokens = shop.getYMTokens();
        YMToken YMTokenExist = shopService.findTokenById(shop, tokenId);
        if (YMTokenExist != null) {
            YMTokens.remove(YMTokenExist);
            YMToken.setId(tokenId);
        }
        YMTokens.add(YMToken);
        shop.setYMTokens(YMTokens);
        shopService.save(shop);

        //tokenService.save(token);

        return "redirect:/shop/" + shop.getId() + "/YMToken/edit/" + YMToken.getId();
    }

    @GetMapping("/edit/{YMToken}")
    public String show(
            Model model,
            @PathVariable(value = "shop") Shop shop,
            @PathVariable(value = "YMToken") String YMTokenId
    ) {
        YMToken YMToken = shopService.findTokenById(shop, YMTokenId);
        if (YMToken == null) {
            return "redirect:/shop/" + shop.getId() + "/YMToken/list";
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
//        System.out.println(YMToken.toString());
        model.addAttribute("YMToken", YMToken);
        model.addAttribute("shop", shop);
        return "/shop/YMToken/edit";
    }

}
