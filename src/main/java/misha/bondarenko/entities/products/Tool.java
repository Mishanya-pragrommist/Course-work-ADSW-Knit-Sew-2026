package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Інструменти для рукоділля (спиці, гачки, ножиці тощо)
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
public class Tool extends Product {

    private String toolType; // Тип інструмента (спиці кругові, гачок, ножиці тощо)
    private String material; // Матеріал
    private String size;     // Розмір або діаметр (наприклад: "3.5 мм", "80 см")

    @Override
    public String render(String indent) {
        return indent + "Інструмент: [" + super.getBaseDetails() +
                ", тип=" + toolType +
                ", матеріал=" + material +
                ", розмір=" + size + "]";
    }
}