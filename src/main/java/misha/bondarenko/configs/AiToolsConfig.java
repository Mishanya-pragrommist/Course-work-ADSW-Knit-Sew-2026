package misha.bondarenko.configs;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import misha.bondarenko.records.dto.CategoryCardDto;
import misha.bondarenko.records.dto.ProductCardDto;
import misha.bondarenko.records.filters.ProductFilter;
import misha.bondarenko.services.CategoryService;
import misha.bondarenko.services.ItemService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

@Configuration
public class AiToolsConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder clientBuilder,
                                 ChatMemory chatMemory) {
        return clientBuilder
                .defaultAdvisors(MessageChatMemoryAdvisor
                                .builder(chatMemory).build())
                .build();
    }

    // Створюємо сховище в оперативній пам'яті для ШІ
    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .maxMessages(20)
                .build();
    }

    // =======================================================================
    // 1. Інструмент для отримання списку категорій
    // =======================================================================

    // Порожній запит, оскільки нам просто потрібен список
    public record CategoryRequest() {}

    @Bean
    @Description("Отримати список усіх каталогів (категорій) товарів. " +
            "Використовуй це, щоб знайти правильний catalogId перед пошуком конкретних товарів." +
            "При наданні відповіді інформацію про ідентифікатори каталогів НЕ виводь")
    public Function<CategoryRequest, List<CategoryCardDto>> getCategoriesTool(CategoryService categoryService) {
        return request -> categoryService.getCategoryCards();
    }

    // =======================================================================
    // 2. Інструмент для фільтрації та пошуку товарів
    // =======================================================================

    /**
     * DTO-запит, який ШІ буде заповнювати самостійно на основі тексту користувача.
     * Містить лише найпопулярніші семантичні атрибути пошуку.
     */
    public record ProductSearchRequest(
            @JsonPropertyDescription("ID каталогу, у якому шукати (обов'язково дізнайся його через getCategoriesTool)")
            Long catalogId,

            @JsonPropertyDescription("Ключове слово для пошуку в назві товару")
            String name,

            @JsonPropertyDescription("Артикул товару, наприклад ART-101")
            String article,

            @JsonPropertyDescription("Мінімальна ціна у гривнях")
            BigDecimal minPrice,

            @JsonPropertyDescription("Максимальна ціна у гривнях")
            BigDecimal maxPrice,

            @JsonPropertyDescription("Бренд товару")
            String brand,

            @JsonPropertyDescription("Колір товару (наприклад: червоний, синій, меланж)")
            String color,

            @JsonPropertyDescription("Склад пряжі або тканини (наприклад: вовна, бавовна, акрил)")
            String composition,

            @JsonPropertyDescription("Матеріал інструментів чи фурнітури (наприклад: дерево, метал, пластик)")
            String material,

            @JsonPropertyDescription("Розмір (наприклад: діаметр спиць/гачків '4мм')")
            String size,

            @JsonPropertyDescription("Рівень складності (тільки для схем/майстер-класів: " +
                    "'початковий', 'середній', 'складний')")
            String difficultyLevel,

            @JsonPropertyDescription("Встанови true, якщо користувач шукає наповнювач для дитячих іграшок або алергіків")
            Boolean isHypoallergenic
    ) {}

    @Bean
    @Description("Шукати товари у визначеному каталозі." +
            " Завжди спочатку дізнавайся catalogId за допомогою getCategoriesTool. " +
            "Усі параметри можуть бути задані як null, окрім catalogId")
    public Function<ProductSearchRequest, List<ProductCardDto>> searchProductsTool(ItemService itemService) {
        return request -> {

            // Створюємо великий системний фільтр, заповнюючи лише ті поля, що передав ШІ.
            // Інші поля залишаємо null.
            ProductFilter aiFilter = new ProductFilter(
                    request.name(),              // name
                    request.minPrice(),          // minPrice
                    request.maxPrice(),          // maxPrice
                    true,                        // isAvailable (ШІ завжди має шукати те, що є в наявності)
                    request.brand(),             // brand
                    null,                        // supplier
                    request.article(),           // article

                    request.color(),             // color
                    request.material(),          // material
                    request.size(),              // size
                    request.composition(),       // composition
                    null,                        // country
                    null,                        // minDensity
                    null,                        // maxDensity

                    // Специфічні типи (залишаємо null, бо ШІ шукає за catalogId та name)
                    null, null, null, null,
                    null, null, null, null,

                    null,                        // maxWeightKg
                    null,                        // author
                    null,                        // publisher
                    null,                        // publicationYear
                    request.difficultyLevel(),   // difficultyLevel
                    null,                        // format
                    null,                        // language

                    request.isHypoallergenic()   // isHypoallergenic
            );

            // Викликаємо метод для фільтрації.
            // За замовчуванням сортуємо за зростанням ціни
            return itemService.getProductCardsDtoFiltered(
                    request.catalogId(),
                    aiFilter,
                    "totalPrice",
                    "asc"
            );
        };
    }
}
