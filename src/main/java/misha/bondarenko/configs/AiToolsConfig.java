package misha.bondarenko.configs;

import misha.bondarenko.records.dto.CategoryCardDto;
import misha.bondarenko.records.dto.ProductCardDto;
import misha.bondarenko.records.filters.ProductFilter;
import misha.bondarenko.services.CategoryService;
import misha.bondarenko.services.ItemService;
import misha.bondarenko.services.interfaces.ICategoryService;
import misha.bondarenko.services.interfaces.IItemService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

@Configuration
public class AiToolsConfig {

    // =======================================================================
    // 1. Інструмент для отримання списку категорій
    // =======================================================================

    // Порожній запит, оскільки нам просто потрібен список
    public record CategoryRequest() {}

    @Bean
    @Description("Отримати список усіх каталогів (категорій) товарів. Використовуй це, щоб знайти правильний catalogId перед пошуком конкретних товарів.")
    public Function<CategoryRequest, List<CategoryCardDto>> getCategoriesTool(CategoryService categoryService) {
        return request -> categoryService.getCategoryCards();
    }

    // =======================================================================
    // 2. Інструмент для фільтрації та пошуку товарів
    // =======================================================================

    /**
     * DTO-запит, який ШІ буде заповнювати самостійно на основі тексту користувача.
     * Ми виносимо сюди лише найпопулярніші поля для спрощення роботи ШІ.
     */
    public record ProductSearchRequest(
            Long catalogId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String color,
            String material
    ) {}

    @Bean
    @Description("Шукати товари у визначеному каталозі. Завжди спочатку дізнавайся catalogId за допомогою getCategoriesTool. Можна вказати minPrice, maxPrice, color або material.")
    public Function<ProductSearchRequest, List<ProductCardDto>> searchProductsTool(ItemService itemService) {
        return request -> {

            // Перетворюємо запит від ШІ у ваш складний ProductFilter
            ProductFilter aiFilter = new ProductFilter(
                    request.minPrice(),
                    request.maxPrice(),
                    true, // Шукаємо тільки ті, що є в наявності (isAvailable = true)
                    null, null, null, // brand, supplier, article
                    request.color(),
                    request.material(),
                    null, null, null,
                    null, null, null,
                    null, null, null,
                    null, null,
                    null, null, null,
                    null, null, null, null,
                    null,  null, null
            );

            // Викликаємо ваш існуючий метод для фільтрації!
            // За замовчуванням сортуємо за ціною за зростанням
            return itemService.getProductCardsDtoFiltered(
                    request.catalogId(),
                    aiFilter,
                    "totalPrice",
                    "ASC"
            );
        };
    }
}
