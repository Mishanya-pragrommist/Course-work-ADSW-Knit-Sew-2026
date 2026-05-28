package misha.bondarenko.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/cart")
    public String viewCart() {
        return "cart";
    }
}
