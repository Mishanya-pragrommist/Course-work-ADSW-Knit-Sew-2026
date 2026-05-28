package misha.bondarenko.records.dto;

public record KitComponentCardDto(
        ProductCardDto product, // Загальні дані про товар в наборі
        int quantity            // Кількість товару
)
{ }
