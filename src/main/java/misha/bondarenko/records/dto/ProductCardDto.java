package misha.bondarenko.records.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Картка товару. Містить основні дані для відображення на сторінці каталогу в сітці товарів.
 * Не містить специфічні атрибути типу "колір", "потужність" тощо, оскільки в сітці їх не треба показувати
 * @param id номер товару в БД (на сторінці не відображається, але юзається для роботи з кошиком)
 * @param article артикул товару
 * @param name повна назва товару (типу "Маркер-кільце Prym, срібний, метал, діаметр Універсальний, ART-305")
 * @param description опис товару
 * @param oldPrice початкова ціна (без знижок)
 * @param discount знижка
 * @param newPrice загальна ціна товару з урахуванням знижки
 * @param isAvailable чи доступний товар для продажу
 * @param stockQuantity кількість товару на складі
 * @param imageUrl посилання на зображення в папці /static/images/
 * @param kitComponents складові набору товарів. Якщо передається звичайний Product, kitComponents = null
 */
public record ProductCardDto(
        Long id,
        String article,
        String name,
        String description,
        BigDecimal oldPrice,
        BigDecimal discount,
        BigDecimal newPrice,
        int stockQuantity,
        boolean isAvailable,
        String imageUrl,
        List<KitComponentCardDto> kitComponents
)
{ }
