package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Фурнітура та аксесуари (маркери, ґудзики, голки, тощо)
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
public class Accessory extends Product {

    private String accessoryType; // Тип аксесуара (наприклад "Маркер петель", "Ґудзик", "Голка")
    private String material; // Матеріал (пластик, метал, дерево тощо)
    private String size; // Розмір (15см, 5мм, №3 - розмір голки, тощо)
    private String color; // Колір

    @Override
    public String render(String indent) {
        return indent + "Фурнітура: [" + super.getBaseDetails() +
                ", тип аксесуара=" + accessoryType +
                ", матеріал=" + material +
                ", розмір=" + size +
                ", колір=" + color + "]";
    }
}