package misha.bondarenko.controllers;

import misha.bondarenko.services.CatalogService;
import misha.bondarenko.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private CatalogService catalogService;
    @Autowired
    private ItemService itemService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("catalogsRecords", catalogService.getCatalogCards());
        return "home";
    }

    @GetMapping("/about-us")
    public String aboutUs(Model model) {
        return "about-us";
    }
}
