package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Швейні нитки
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
public class SewingThread extends Product {

    private String threadType; // Тип нитки (наприклад: "Універсальна", "Армована", "Оверлочна")
    private String composition; // Склад (наприклад: "100% поліестер", "Бавовна")
    private String thickness; // Товщина/Номер (наприклад: "№40", "120")
    private int lengthInMeters; // Довжина намотування в метрах
    private String color; // Колір або номер кольору за палітрою

    @Override
    public String render(String indent) {
        return indent + "Швейні нитки: [" + super.getBaseDetails() +
                ", тип=" + threadType +
                ", склад=" + composition +
                ", товщина=" + thickness +
                ", намотування=" + lengthInMeters + " м" +
                ", колір=" + color + "]";
    }
}