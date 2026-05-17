package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Наповнювачі та ущільнювачі (синтепух, флізелін, дублерин тощо)
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
public class Filler extends Product {

    private String fillerType; // Тип (наприклад: "Синтепух", "Флізелін", "Дублерин")
    private int density; // Щільність (г/м2)
    private boolean isHypoallergenic; // Гіпоалергенність
    private double packageWeightKg; // Вага пакування в кілограмах

    @Override
    public String render(String indent) {
        return indent + "Наповнювач/Ущільнювач: [" + super.getBaseDetails() +
                ", тип=" + fillerType +
                ", щільність=" + density + " г/м2" +
                ", гіпоалергенний=" + (isHypoallergenic ? "Так" : "Ні") +
                ", вага пакування=" + packageWeightKg + " кг]";
    }
}