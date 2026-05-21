package misha.bondarenko.controllers;

import misha.bondarenko.records.dto.CatalogCardDto;
import misha.bondarenko.records.dto.ProductCardDto;
import misha.bondarenko.records.filters.ProductFilter;
import misha.bondarenko.services.CatalogService;
import misha.bondarenko.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
            List<ProductCardDto> productCardDtoList = productService.getProductCardsDto(id);

            // Передаємо маркер категорії (назву або спеціальний enum/код)
            model.addAttribute("categoryName", catalogCardById.name());

            // Порожній DTO для збору даних з форми
            model.addAttribute("filter", new ProductFilter(
                    null, null, null, null, null,
                    null, null, null, null, null,
                    null, null, null, null, null,
                    null, null, null, null, null,
                    null, null, null, null, null, null, null));

            model.addAttribute("catalog", catalogCardById);
            model.addAttribute("products", productCardDtoList);

            System.out.println("Size of list: " + productCardDtoList.size());

            return "products-list";
        }
        catch (NumberFormatException e) {
            return "redirect:/"; // Go back to home page
        }
    }

    @GetMapping("/catalog/{id}/filter")
    public String filterProducts(@PathVariable String id,
                                 @ModelAttribute ProductFilter filter,
                                 @RequestParam(required = false, defaultValue = "name") String sortBy,
                                 @RequestParam(required = false, defaultValue = "asc") String direction,
                                 Model model) {

        Long catalogId = Long.valueOf(id);
        CatalogCardDto catalogCardById = catalogService.getCatalogCardById(catalogId);
        // Отримуємо відфільтровані та відсортовані картки
        List<ProductCardDto> products = productService.getProductCardsDtoFiltered(
                catalogId, filter, sortBy, direction
        );

        // Передаємо результати та поточний стан фільтрів на фронтенд
        model.addAttribute("products", products);
        model.addAttribute("filter", filter);
        model.addAttribute("catalog", catalogCardById);

        // Передаємо параметри сортування для підсвічування активних кнопок у UI
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);

        // Повертаємо назву шаблону (наприклад, сторінку каталогу з оновленими товарами)
        return "products-list";
    }
}
