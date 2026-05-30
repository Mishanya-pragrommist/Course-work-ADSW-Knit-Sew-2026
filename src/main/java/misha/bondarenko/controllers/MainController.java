package misha.bondarenko.controllers;

import misha.bondarenko.services.interfaces.ICategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Контролер для переходу до каталогу товарів,
 * для переходу на інформаційні сторінки типу "Контакти" та "Оплата і доставка",
 * а також для переходу на сторінку із кошиком
 */
@Controller
public class MainController {

    private final ICategoryService categoryService;

    public MainController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("catalogsRecords", categoryService.getCategoryCards());
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

    @GetMapping("/cart")
    public String viewCart(@RequestHeader(value = "referer", required = false) String referer,
                           Model model) {
        // Дефолтна сторінка для повернення, якщо користувач прийшов за прямим посиланням
        String backUrl = "/catalog";

        // Перевіряємо: якщо referer існує і це не посилання на сам кошик (щоб уникнути зациклення при рефреші сторінки)
        if (referer != null && !referer.contains("/cart")) {
            backUrl = referer;
        }

        // Передаємо URL у Thymeleaf
        model.addAttribute("backUrl", backUrl);

        return "cart";
    }
}
