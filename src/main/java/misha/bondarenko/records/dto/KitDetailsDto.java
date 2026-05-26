package misha.bondarenko.records.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * DTO для відображення повної інформації про набір на його персональній сторінці
 */
public record KitDetailsDto(
        Long id,
        String article,              // Артикул
        String name,                 // Назва
        String description,          // Повний опис

        // Ціни (вже розраховані та округлені)
        BigDecimal originalPrice,    // Базова ціна (до знижки, для перекреслення)
        BigDecimal finalPrice,       // Фінальна ціна (після знижки)
        BigDecimal discount,         // Відсоток знижки (якщо є)

        // Статус та наявність
        boolean isAvailable,
        int stockQuantity,           // Кількість на складі (для обмеження вводу в кошик)
        String unitDescription,      // Одиниці вимірювання (шт., метри, кг)

        // Медіа
        String imageURL,

        Map<String, String> attributes,
        List<ProductCardDto> kitComponents
)
{ }