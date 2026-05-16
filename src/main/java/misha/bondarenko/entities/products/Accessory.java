package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Фурнітура та аксесуари (маркери, ґудзики, голки, тощо)
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Accessory extends Product {

    private String accessoryType; // Тип аксесуара (наприклад "Маркер петель", "Ґудзик", "Голка")
    private String material; // Матеріал (пластик, метал, дерево тощо)
    private String size; // Розмір (15см, 5мм, №3 - розмір голки, тощо)
    private String color; // Колір

    public Accessory(boolean isAvailable,
                     String article,
                     String name,
                     String description,
                     String supplier,
                     String brand,
                     int stockQuantity,
                     MeasureUnit unit,
                     BigDecimal price,
                     BigDecimal discount,
                     String accessoryType,
                     String material,
                     String size,
                     String color) {
        super(isAvailable, article, name, description, supplier, brand, stockQuantity, unit, price, discount);
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
}