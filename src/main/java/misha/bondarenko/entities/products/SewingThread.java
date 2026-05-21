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

    public SewingThread(Long id, String name, String description, BigDecimal price, BigDecimal discount
            , MeasureUnit unit, int stockQuantity, boolean isAvailable, String imageUrl,
                        String article, String supplier, String brand, String threadType,
                        String composition, String thickness, int lengthInMeters, String color) {
        super(id, article, name, description, price, discount, unit,
                stockQuantity, isAvailable, imageUrl, null, supplier, brand);
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