package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.*;
import misha.bondarenko.enums.MeasureUnit;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fabric extends Product {

    private String fabricType; // Тип тканини (бавовна, трикотаж, шовк тощо)
    private String composition; // Склад (наприклад: "100% бавовна")
    private int widthInCm; // Ширина рулону в сантиметрах
    private int density; // Щільність тканини (г/м2)
    private String color; // Колір або опис принту

    public Fabric(boolean isAvailable,
                  String code,
                  String article,
                  String supplier,
                  String brand,
                  int stockQuantity,
                  MeasureUnit unit,
                  BigDecimal price,
                  BigDecimal discount,
                  String fabricType,
                  String composition,
                  int widthInCm,
                  int density,
                  String color) {
        super(isAvailable, code, article, supplier, brand, stockQuantity, unit, price, discount);
        this.fabricType = fabricType;
        this.composition = composition;
        this.widthInCm = widthInCm;
        this.density = density;
        this.color = color;
    }

    @Override
    public String render(String indent) {
        return indent + "Тканина: [" + super.getBaseDetails() +
                ", тип=" + fabricType +
                ", склад=" + composition +
                ", ширина=" + widthInCm + " см" +
                ", щільність=" + density + " г/м2" +
                ", колір=" + color + "]";
    }

    @Override
    public String renderName() {
        return "Тканина " + fabricType.toLowerCase() + " " + brand + ", колір " + color.toLowerCase() + ", "
                + composition + " " + widthInCm + " см";
    }

}
