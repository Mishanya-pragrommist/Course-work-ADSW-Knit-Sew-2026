package misha.bondarenko.records.dto;

import misha.bondarenko.entities.products.KitComponent;

import java.math.BigDecimal;
import java.util.List;

public record ProductCardDto(
        Long id,
        String article,
        String name,
        String description,
        BigDecimal oldPrice,
        BigDecimal discount,
        BigDecimal newPrice,
        boolean isAvailable,
        String imageUrl,
        List<KitComponentCardDto> kitComponents // Якщо даний товар є набором
)
{ }
