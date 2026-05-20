package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import misha.bondarenko.enums.MeasureUnit;

import java.math.BigDecimal;

/**
 * Швейні нитки
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class SewingThread extends Product {

    private String threadType; // Тип нитки (наприклад: "Універсальна", "Армована", "Оверлочна")
    private String composition; // Склад (наприклад: "100% поліестер", "Бавовна")
    private String thickness; // Товщина/Номер (наприклад: "№40", "120")
    private int lengthInMeters; // Довжина намотування в метрах
    private String color; // Колір або номер кольору за палітрою

    public SewingThread(boolean isAvailable, String code, String article, String supplier,
                        String brand, int stockQuantity, MeasureUnit unit, BigDecimal price, BigDecimal discount,
                        String threadType, String composition, String thickness, int lengthInMeters, String color) {
        super(isAvailable, code, article, supplier, brand, stockQuantity, unit, price, discount);
        this.threadType = threadType;
        this.composition = composition;
        this.thickness = thickness;
        this.lengthInMeters = lengthInMeters;
        this.color = color;
    }

    @Override
    public String render(String indent) {
        return indent + "Швейні нитки: [" + super.getBaseDetails() +
                ", тип=" + threadType +
                ", склад=" + composition +
                ", товщина=" + thickness +
                ", намотування=" + lengthInMeters + " м" +
                ", колір=" + color + "]";
    }

    @Override
    public String renderName() {
        return "Нитка " + threadType + " " + brand + ", " +
                color.toLowerCase() + ", товщина " + thickness + ", довжина" + lengthInMeters + "м";
    }
}