package misha.bondarenko.entities.products;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Column(nullable = false)
    private String equipmentType; // Тип обладнання
    @Size(min = 1)
    private int warrantyMonths; // Гарантійний термін у місяцях

    @Size(min = 1)
    private int powerWatt; // Споживана потужність у ватах

    @Column(nullable = false)
    private String dimensions; // Габарити ("40x30x20 см")
    private double weightKg; // Вага в кілограмах

    @Size(min = 1)
    private int operationsCount; // Кількість швейних/в'язальних операцій

    public Equipment(Long id, String name, String description, BigDecimal price, BigDecimal discount,
                     MeasureUnit unit, int stockQuantity, boolean isAvailable, String imageUrl,
                     String article, String supplier, String brand, String country, String equipmentType,
                     int warrantyMonths, int powerWatt, String dimensions, double weightKg, int operationsCount) {

        super(id, article, name, description, price, discount, unit,
                stockQuantity, isAvailable, imageUrl, null, supplier, brand, country);
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

    @Override
    public String renderName() {
        return equipmentType + " " + brand + ", " + operationsCount + " опер., "
                + powerWatt + "W, гарантія: " + warrantyMonths + " міс., " + article;
    }

}
