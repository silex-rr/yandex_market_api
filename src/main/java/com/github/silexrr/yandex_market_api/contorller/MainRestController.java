package com.github.silexrr.yandex_market_api.contorller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest")
public class MainRestController {

    @GetMapping("/")
    public String main () {
        return "Api";
    }

}
