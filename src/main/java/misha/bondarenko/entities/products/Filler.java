package misha.bondarenko.entities.products;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import misha.bondarenko.enums.MeasureUnit;

import java.math.BigDecimal;

/**
 * Наповнювачі та ущільнювачі (синтепух, флізелін, дублерин тощо)
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filler extends Product {

    @Column(nullable = false)
    private String fillerType; // Тип (наприклад: "Синтепух", "Флізелін", "Дублерин")

    @Size(min = 1)
    private int density; // Щільність (г/м2)
    private boolean isHypoallergenic; // Гіпоалергенність
    private double packageWeightKg; // Вага пакування в кілограмах

    public Filler(Long id, String name, String description, BigDecimal price, BigDecimal discount,
                  MeasureUnit unit, int stockQuantity, boolean isAvailable, String imageUrl,
                  String article, String supplier, String brand, String country,
                  String fillerType, int density, boolean isHypoallergenic, double packageWeightKg) {

        super(id, article, name, description, price, discount, unit,
                stockQuantity, isAvailable, imageUrl, null, supplier, brand, country);
        this.fillerType = fillerType;
        this.density = density;
        this.isHypoallergenic = isHypoallergenic;
        this.packageWeightKg = packageWeightKg;
    }

    @Override
    public String render(String indent) {
        return indent + "Наповнювач/Ущільнювач: [" + super.getBaseDetails() +
                ", тип=" + fillerType +
                ", щільність=" + density + " г/м2" +
                ", гіпоалергенний=" + (isHypoallergenic ? "Так" : "Ні") +
                ", вага пакування=" + packageWeightKg + " кг]";
    }

    @Override
    public String renderName() {
        return name + ", " + brand + ", " + density + " г/м², пакування "
                + packageWeightKg + " кг, " + article;
    }
}