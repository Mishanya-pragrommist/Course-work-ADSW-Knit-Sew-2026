package misha.bondarenko.entities.products;

import jakarta.persistence.Column;
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

    @Column(nullable = false)
    private String toolType; // Тип інструмента (спиці кругові, гачок, ножиці тощо)

    @Column(nullable = false)
    private String material; // Матеріал

    @Column(nullable = false)
    private String size;     // Розмір або діаметр (наприклад: "3.5 мм", "80 см")

    public Tool(Long id, String name, String description, BigDecimal price, BigDecimal discount,
                MeasureUnit unit, int stockQuantity, boolean isAvailable, String imageUrl,
                String article, String supplier, String brand, String country,
                String toolType, String material, String size) {
        super(id, name, article, description, price, discount, unit,
                stockQuantity, isAvailable, imageUrl, null, supplier, brand, country);
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
        return toolType + " " + brand + ", " +
                material.toLowerCase() + ", розмір " + size + ", " + article;
    }
}