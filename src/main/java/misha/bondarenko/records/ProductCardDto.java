package misha.bondarenko.records;

import java.math.BigDecimal;

public record ProductCardDto(
        Long id,
        String name,
        BigDecimal oldPrice,
        BigDecimal discount,
        BigDecimal newPrice,
        boolean isAvailable,
        String imageUrl
)
{ }
