package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.*;
import misha.bondarenko.enums.MeasureUnit;

import java.math.BigDecimal;

/**
 * Інструменти для рукоділля (спиці, гачки, ножиці тощо)
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tool extends Product {

    private String toolType; // Тип інструмента (спиці кругові, гачок, ножиці тощо)
    private String material; // Матеріал
    private String size;     // Розмір або діаметр (наприклад: "3.5 мм", "80 см")

    public Tool(boolean isAvailable,
                String code,
                String article,
                String supplier,
                String brand,
                int stockQuantity,
                MeasureUnit unit,
                BigDecimal price,
                BigDecimal discount,
                String toolType,
                String material,
                String size) {
        super(isAvailable, code, article, supplier, brand, stockQuantity, unit, price, discount);
        this.toolType = toolType;
        this.material = material;
        this.size = size;
    }

    @Override
    public String render(String indent) {
        return indent + "Інструмент: [" + super.getBaseDetails() +
                ", тип=" + toolType +
                ", матеріал=" + material +
                ", розмір=" + size + "]";
    }

    @Override
    public String renderName() {
        return toolType + " " + brand + ", " + material.toLowerCase() + ", розмір " + size;
    }
}