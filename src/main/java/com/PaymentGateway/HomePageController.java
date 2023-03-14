package com.PaymentGateway;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
    @GetMapping("")
    public String getHomePage() {
        return "home_page";
    }
}
