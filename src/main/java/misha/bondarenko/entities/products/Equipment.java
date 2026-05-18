package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import misha.bondarenko.enums.MeasureUnit;

import java.math.BigDecimal;

/**
 * Обладнання (швейні машини, оверлоки тощо)
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Equipment extends Product {

    private String equipmentType; // Тип обладнання
    private int warrantyMonths; // Гарантійний термін у місяцях
    private int powerWatt; // Споживана потужність у ватах
    private String dimensions; // Габарити ("40x30x20 см")
    private double weightKg; // Вага в кілограмах
    private int operationsCount; // Кількість швейних/в'язальних операцій

    public Equipment(boolean isAvailable,
                     String code,
                     String article,
                     String supplier,
                     String brand,
                     int stockQuantity,
                     MeasureUnit unit,
                     BigDecimal price,
                     BigDecimal discount,
                     String equipmentType,
                     int warrantyMonths,
                     int powerWatt,
                     String dimensions,
                     double weightKg,
                     int operationsCount) {
        super(isAvailable, code, article, supplier, brand, stockQuantity, unit, price, discount);
        this.equipmentType = equipmentType;
        this.warrantyMonths = warrantyMonths;
        this.powerWatt = powerWatt;
        this.dimensions = dimensions;
        this.weightKg = weightKg;
        this.operationsCount = operationsCount;
    }

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
