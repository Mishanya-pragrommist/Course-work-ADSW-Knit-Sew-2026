package misha.bondarenko.entities.products;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.*;
import misha.bondarenko.enums.MeasureUnit;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fabric extends Product {

    @Column(nullable = false)
    private String fabricType; // Тип тканини (бавовна, трикотаж, шовк тощо)

    @Column(nullable = false)
    private String composition; // Склад (наприклад: "100% бавовна")

    @Size(min = 1)
    private int widthInCm; // Ширина рулону в сантиметрах

    @Size(min = 1)
    private int density; // Щільність тканини (г/м2)

    @Column(nullable = false)
    private String color; // Колір або опис принту

    public Fabric(Long id, String name, String description, BigDecimal price, BigDecimal discount,
                  MeasureUnit unit, int stockQuantity, boolean isAvailable, String imageUrl,
                  String article, String supplier, String brand, String country,
                  String fabricType, String composition,
                  int widthInCm, int density, String color) {

        super(id, article, name, description, price, discount, unit,
                stockQuantity, isAvailable, imageUrl, null, supplier, brand, country);
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
        return "Тканина " + fabricType.toLowerCase() + " " + brand + ", " + color.toLowerCase() + ", "
                + composition + ", ширина рул. " + widthInCm + " см" + ", " + article;
    }

}
