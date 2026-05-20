package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.*;
import misha.bondarenko.enums.MeasureUnit;

import java.math.BigDecimal;

/**
 * Пряжа (нитки для в'язання)
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Yarn extends Product {

    private String fiberContent; // Склад волокна (100% меринос, 50% вовна, 50% акрил)
    private int lengthInMeters; // Довжина нитки в метрах
    private int weightInGrams; // Вага мотка в грамах
    private String dyeLot; // Партія фарбування (Lot) - важливо для збігу відтінку
    private String color; // Колір
    private String toolsRecommended; // Рекомендовані інструменти

    public Yarn(boolean isAvailable, String code, String article, String supplier,
                String brand, int stockQuantity, MeasureUnit unit,
                BigDecimal price, BigDecimal discount, String fiberContent,
                int lengthInMeters, int weightInGrams, String dyeLot, String color) {
        super(isAvailable, code, article, supplier, brand, stockQuantity, unit, price, discount);
        this.fiberContent = fiberContent;
        this.lengthInMeters = lengthInMeters;
        this.weightInGrams = weightInGrams;
        this.dyeLot = dyeLot;
        this.color = color;
    }

    @Override
    public String render(String indent) {
        return indent + "Пряжа: [" + super.getBaseDetails() +
                ", склад=" + fiberContent +
                ", метраж=" + lengthInMeters + "м / " + weightInGrams + "г" +
                ", партія (Lot)=" + dyeLot +
                ", колір=" + color + "]";
    }

    @Override
    public String renderName() {
        return "Пряжа " + supplier + ", " + color.toLowerCase() + ", " + fiberContent + ", " + weightInGrams + "г, " + dyeLot;
    }
}
