package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.*;
import misha.bondarenko.enums.MeasureUnit;

import java.math.BigDecimal;

/**
 * Фурнітура та аксесуари (маркери, ґудзики, голки, тощо)
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Accessory extends Product {

    private String accessoryType; // Тип аксесуара (наприклад "Маркер петель", "Ґудзик", "Голка")
    private String material; // Матеріал (пластик, метал, дерево тощо)
    private String size; // Розмір (15см, 5мм, №3 - розмір голки, тощо)
    private String color; // Колір

    public Accessory(Long id, String article, String name, String description,
                     BigDecimal price, BigDecimal discount, MeasureUnit unit,
                     int stockQuantity, boolean isAvailable, String imageUrl,
                     String supplier, String brand, String country, String accessoryType,
                     String material, String size, String color) {
        super(id, article, name, description, price, discount, unit,
                stockQuantity, isAvailable, imageUrl, null, supplier, brand, country);

        this.accessoryType = accessoryType;
        this.material = material;
        this.size = size;
        this.color = color;
    }

    @Override
    public String render(String indent) {
        return indent + "Фурнітура: [" + super.getBaseDetails() +
                ", тип аксесуара=" + accessoryType +
                ", матеріал=" + material +
                ", розмір=" + size +
                ", колір=" + color + "]";
    }

    @Override
    public String renderName() {
        return accessoryType + " " + brand + ", " + color.toLowerCase() +
                ", " + material.toLowerCase() + ", діаметр " + size;
    }
}