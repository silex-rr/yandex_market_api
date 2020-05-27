package com.github.silexrr.yandex_market_api.api.web;

import com.github.silexrr.yandex_market_api.api.model.APIToken;
import com.github.silexrr.yandex_market_api.api.service.APITokenService;
import com.github.silexrr.yandex_market_api.auth.model.User;
import com.github.silexrr.yandex_market_api.auth.web.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(value = "/api/token")
public class APITokenController {

    @Autowired
    private APITokenService apiTokenService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("list")
    public String list(
            Model model
    ) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<APIToken> tokens = apiTokenService.findByUser(user);
        model.addAttribute("tokens", tokens);

        return "/api/token/list";
    }

    @PostMapping("list")
    public String actions(
            @RequestParam(value = "action") String action,
            @RequestParam(value = "token") Optional<APIToken> apiTokenOptional
    ) {
        APIToken apiToken = null;
        if (apiTokenOptional.isEmpty() == false) {
            apiToken = apiTokenOptional.get();
        }
        switch (action) {
            case "addNew" :
                User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Map<String, Object> claims = new HashMap<>();
//                claims.put("shopId", shop.getId());
                String token = jwtTokenUtil.generateToken(user, claims);

                Date expirationDateFromToken = jwtTokenUtil.getExpirationDateFromToken(token);

                apiToken = new APIToken();
                apiToken.setUser(user);
                apiToken.setExpireTo(expirationDateFromToken);
                apiToken.setToken(token);
                apiToken.setEnable(true);

                apiTokenService.save(apiToken);
                break;
            case "enable" :
                if (apiToken == null) {
                    break;
                }
                apiToken.setEnable(true);

                apiTokenService.save(apiToken);
                break;
            case "disable" :
                if (apiToken == null) {
                    break;
                }
                apiToken.setEnable(false);

                apiTokenService.save(apiToken);
                break;
            case "delete" :
                if (apiToken == null) {
                    break;
                }
                apiTokenService.delete(apiToken);
                break;
        }

        return "redirect:/api/token/list";
    }

}
