package misha.bondarenko.controllers;

import misha.bondarenko.records.dto.CategoryCardDto;
import misha.bondarenko.records.dto.ProductCardDto;
import misha.bondarenko.records.filters.ProductFilter;
import misha.bondarenko.services.interfaces.ICategoryService;
import misha.bondarenko.services.interfaces.IItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class CatalogController {

    private final ICategoryService categoryService;
    private final IItemService itemService;

    public CatalogController(ICategoryService categoryService, IItemService itemService) {
        this.categoryService = categoryService;
        this.itemService = itemService;
    }

    @GetMapping("/category/{catId}")
    public String showProducts(@PathVariable String catId,
                               ProductFilter productFilter,
                               Model model) {
        try {
            Long id = Long.parseLong(catId);
            CategoryCardDto categoryCard = categoryService.getCategoryCardById(id);
            List<ProductCardDto> productCardDtoList = itemService.getProductCardsDto(id);

            model.addAttribute("filter", productFilter); // Порожній фільтр для збору даних з форми
            model.addAttribute("category", categoryCard);
            model.addAttribute("products", productCardDtoList);

            return "products-list";
        }
        catch (Exception e) {
            return "redirect:/"; // Go back to home page
        }
    }

    @GetMapping("/category/{id}/filter")
    public String filterProducts(@PathVariable String id,
                                 @ModelAttribute ProductFilter filter,
                                 @RequestParam(required = false, defaultValue = "name") String sortBy,
                                 @RequestParam(required = false, defaultValue = "asc") String direction,
                                 Model model) {

        Long catalogId = Long.valueOf(id);
        CategoryCardDto categoryCardById = categoryService.getCategoryCardById(catalogId);
        System.out.println("sort by: " + sortBy + ", direction: " + direction);

        List<ProductCardDto> products = itemService.getProductCardsDtoFiltered(
                catalogId, filter, sortBy, direction
        );

        // Передаємо результати та поточний стан фільтрів на фронтенд
        model.addAttribute("products", products);
        model.addAttribute("filter", filter);
        model.addAttribute("category", categoryCardById);

        // Передаємо параметри сортування
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);

        return "products-list";
    }


    @GetMapping("/category/{catId}/product/{id}")
    public String getProductPage(@PathVariable Long catId,
                                 @PathVariable Long id,
                                 Model model) {
        Map<String, String> productAttributes = itemService.getProductDetailsMap(id);
        ProductCardDto productCardDto = itemService.getProductCardById(id);
        CategoryCardDto categoryCardDto = categoryService.getCategoryCardById(catId);

        if (productAttributes == null) {
            return "redirect:/"; // Або перенаправлення на сторінку 404
        }

        model.addAttribute("productAttributes", productAttributes);
        model.addAttribute("product", productCardDto);
        model.addAttribute("category", categoryCardDto);
        return "product";
    }
}
