package misha.bondarenko.records.dto;

import java.math.BigDecimal;

public record ProductCardDto(
        Long id,
        String article,
        String name,
        String description,
        BigDecimal oldPrice,
        BigDecimal discount,
        BigDecimal newPrice,
        boolean isAvailable,
        String imageUrl
)
{ }
