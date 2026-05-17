package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Fabric extends Product {

    private String fabricType; // Тип тканини (бавовна, трикотаж, шовк тощо)
    private String composition; // Склад (наприклад: "100% бавовна")
    private int widthInCm; // Ширина рулону в сантиметрах
    private int density; // Щільність тканини (г/м2)
    private String color; // Колір або опис принту

    @Override
    public String render(String indent) {
        return indent + "Тканина: [" + super.getBaseDetails() +
                ", тип=" + fabricType +
                ", склад=" + composition +
                ", ширина=" + widthInCm + " см" +
                ", щільність=" + density + " г/м2" +
                ", колір=" + color + "]";
    }
}
