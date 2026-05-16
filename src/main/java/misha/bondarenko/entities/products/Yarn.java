package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Пряжа (нитки для в'язання)
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Yarn extends Product {

    private String fiberContent; // Склад волокна (100% меринос, 50% вовна, 50% акрил)
    private int lengthInMeters; // Довжина нитки в метрах
    private int weightInGrams; // Вага мотка в грамах
    private String dyeLot; // Партія фарбування (Lot) - важливо для збігу відтінку
    private String color; // Колір

    public Yarn(boolean isAvailable,
                String article,
                String name,
                String description,
                String supplier,
                String brand,
                int stockQuantity,
                MeasureUnit unit,
                BigDecimal price,
                BigDecimal discount,
                String fiberContent,
                int lengthInMeters,
                int weightInGrams,
                String dyeLot,
                String color) {
        super(isAvailable, article, name, description, supplier, brand, stockQuantity, unit, price, discount);
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
}
