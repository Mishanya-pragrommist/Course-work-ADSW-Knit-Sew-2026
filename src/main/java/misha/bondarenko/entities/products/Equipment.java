package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Обладнання (швейні машини, оверлоки тощо)
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
public class Equipment extends Product {

    private String equipmentType; // Тип обладнання
    private int warrantyMonths; // Гарантійний термін у місяцях
    private int powerWatt; // Споживана потужність у ватах
    private String dimensions; // Габарити ("40x30x20 см")
    private double weightKg; // Вага в кілограмах
    private int operationsCount; // Кількість швейних/в'язальних операцій

    @Override
    public String render(String indent) {
        return indent + "Обладнання: [" + super.getBaseDetails() +
                ", тип=" + equipmentType +
                ", гарантія=" + warrantyMonths + " міс." +
                ", потужність=" + powerWatt + " Вт" +
                ", габарити=" + dimensions +
                ", вага=" + weightKg + " кг" +
                ", кількість операцій=" + operationsCount + "]";
    }
}
