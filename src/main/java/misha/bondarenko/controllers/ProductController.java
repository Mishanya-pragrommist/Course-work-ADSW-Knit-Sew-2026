package misha.bondarenko.controllers;

import misha.bondarenko.records.dto.ProductCardDto;
import misha.bondarenko.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String getProductPage(@PathVariable Long id, Model model) {
        ProductCardDto product = productService.getProductCardById(id);

        if (product == null) {
            return "redirect:/"; // Або перенаправлення на сторінку 404
        }

        model.addAttribute("product", product);
        return "product";
    }
}