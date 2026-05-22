package misha.bondarenko.controllers;

import misha.bondarenko.services.CatalogService;
import misha.bondarenko.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контролер для переходу до каталогу товарів та
 * для переходу на нефункціональні сторінки типу "Контакти" та "Оплата і доставка"
 */
@Controller
public class PageController {

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

    @GetMapping("/contacts")
    public String contacts(Model model) {
        return "contacts";
    }

    @GetMapping("/payment-delivery")
    public String paymentDelivery(Model model) {
        return "payment-delivery";
    }
}
