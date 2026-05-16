package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Інструменти для рукоділля (спиці, гачки, ножиці тощо)
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Tool extends Product {

    private String toolType; // Тип інструмента (спиці кругові, гачок, ножиці тощо)
    private String material; // Матеріал
    private String size;     // Розмір або діаметр (наприклад: "3.5 мм", "80 см")

    public Tool(boolean isAvailable,
                String article,
                String name,
                String description,
                String supplier,
                String brand,
                int stockQuantity,
                MeasureUnit unit,
                BigDecimal price,
                BigDecimal discount,
                String toolType,
                String material,
                String size) {
        super(isAvailable, article, name, description, supplier, brand, stockQuantity, unit, price, discount);
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
}