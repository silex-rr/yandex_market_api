package com.github.silexrr.yandex_market_api.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String main (Model model) {
        model.addAttribute("name", "test");
        return "main";
    }
}
