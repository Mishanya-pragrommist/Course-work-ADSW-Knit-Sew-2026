package misha.bondarenko.controllers;

import misha.bondarenko.records.CatalogCardDto;
import misha.bondarenko.records.ProductCardDto;
import misha.bondarenko.services.CatalogService;
import misha.bondarenko.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CatalogController {

    private final CatalogService catalogService;
    private final ProductService productService;
    public CatalogController(CatalogService catalogService, ProductService productService) {
        this.catalogService = catalogService;
        this.productService = productService;
    }

    @GetMapping("/catalog/{catId}/")
    public String showProducts(@PathVariable String catId,
                               Model model) {
        try {
            Long id = Long.parseLong(catId);
            CatalogCardDto catalogCardById = catalogService.getCatalogCardById(id);
            List<ProductCardDto> productCardDtoList = productService.getProductCards(id);

            model.addAttribute("catalog", catalogCardById);
            model.addAttribute("products", productCardDtoList);

            System.out.println("Size of list: " + productCardDtoList.size());

            return "products-list";
        }
        catch (NumberFormatException e) {
            return "redirect:/"; // Go back to home page
        }
    }
}
