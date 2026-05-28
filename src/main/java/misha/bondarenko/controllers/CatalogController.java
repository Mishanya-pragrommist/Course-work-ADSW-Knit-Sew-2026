package misha.bondarenko.controllers;

import misha.bondarenko.records.dto.CatalogCardDto;
import misha.bondarenko.records.dto.ProductCardDto;
import misha.bondarenko.records.filters.ProductFilter;
import misha.bondarenko.services.CatalogService;
import misha.bondarenko.services.ItemService;
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

    private final CatalogService catalogService;
    private final ItemService itemService;
    public CatalogController(CatalogService catalogService, ItemService itemService) {
        this.catalogService = catalogService;
        this.itemService = itemService;
    }

    @GetMapping("/catalog/{catId}/")
    public String showProducts(@PathVariable String catId,
                               ProductFilter productFilter,
                               Model model) {
        try {
            Long id = Long.parseLong(catId);
            CatalogCardDto catalogCard = catalogService.getCatalogCardById(id);
            List<ProductCardDto> productCardDtoList = itemService.getProductCardsDto(id);

            // Передаємо назву категорії
            model.addAttribute("categoryName", catalogCard.name());

            // Порожній фільтр для збору даних з форми
            model.addAttribute("filter", productFilter);
            model.addAttribute("catalog", catalogCard);
            model.addAttribute("products", productCardDtoList);

            // Для дебагу
            System.out.println("Size of list: " + productCardDtoList.size());
            System.out.println(productCardDtoList.get(0).name());
            return "products-list";
        }
        catch (Exception e) {
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
        System.out.println(filter.minPrice() + " " +  filter.maxPrice() + " " + filter.accessoryType());
        // Отримуємо відфільтровані та відсортовані картки
        List<ProductCardDto> products = itemService.getProductCardsDtoFiltered(
                catalogId, filter, sortBy, direction
        );

        // Передаємо результати та поточний стан фільтрів на фронтенд
        model.addAttribute("products", products);
        model.addAttribute("filter", filter);
        model.addAttribute("catalog", catalogCardById);

        model.addAttribute("categoryName", catalogCardById.name());

        // Передаємо параметри сортування для підсвічування активних кнопок у UI
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);

        // Повертаємо назву шаблону (наприклад, сторінку каталогу з оновленими товарами)
        return "products-list";
    }


    @GetMapping("/product/{id}")
    public String getProductPage(@PathVariable Long id,
                                 Model model) {
        Map<String, String> productAttributes = itemService.getProductDetailsMap(id);
        ProductCardDto productCardDto = itemService.getProductCardById(id);

        if (productAttributes == null) {
            return "redirect:/"; // Або перенаправлення на сторінку 404
        }

        model.addAttribute("productAttributes", productAttributes);
        model.addAttribute("product", productCardDto);
        model.addAttribute("catalogName");
        return "product";
    }
}
