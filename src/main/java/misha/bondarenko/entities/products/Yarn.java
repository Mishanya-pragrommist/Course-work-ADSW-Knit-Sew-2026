package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * Пряжа (нитки для в'язання)
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
public class Yarn extends Product {

    private String fiberContent; // Склад волокна (100% меринос, 50% вовна, 50% акрил)
    private int lengthInMeters; // Довжина нитки в метрах
    private int weightInGrams; // Вага мотка в грамах
    private String dyeLot; // Партія фарбування (Lot) - важливо для збігу відтінку
    private String color; // Колір

    @Override
    public String render(String indent) {
        return indent + "Пряжа: [" + super.getBaseDetails() +
                ", склад=" + fiberContent +
                ", метраж=" + lengthInMeters + "м / " + weightInGrams + "г" +
                ", партія (Lot)=" + dyeLot +
                ", колір=" + color + "]";
    }
}
