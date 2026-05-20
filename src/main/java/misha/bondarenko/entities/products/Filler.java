package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
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

    private String fillerType; // Тип (наприклад: "Синтепух", "Флізелін", "Дублерин")
    private int density; // Щільність (г/м2)
    private boolean isHypoallergenic; // Гіпоалергенність
    private double packageWeightKg; // Вага пакування в кілограмах

    public Filler(boolean isAvailable,
                  String code,
                  String article,
                  String supplier,
                  String brand,
                  int stockQuantity,
                  MeasureUnit unit,
                  BigDecimal price,
                  BigDecimal discount,
                  String fillerType,
                  int density,
                  boolean isHypoallergenic,
                  double packageWeightKg) {
        super(isAvailable, code, article, supplier, brand, stockQuantity, unit, price, discount);
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
        return "Наповнювач " + fillerType + ", " + brand + ", " +
                density + " г/м², пакування " + packageWeightKg + " кг";
    }
}