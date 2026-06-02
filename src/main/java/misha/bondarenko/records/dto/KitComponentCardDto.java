package misha.bondarenko.records.dto;

/**
 * Картка елементу набору. Використовується для відображення набору
 * на сторінці детальної інфи про набір (під таблицею з характеристиками)
 * @param product картка товару
 * @param quantity кількість товару в даному наборі
 */
public record KitComponentCardDto(
        ProductCardDto product,
        int quantity
)
{ }
