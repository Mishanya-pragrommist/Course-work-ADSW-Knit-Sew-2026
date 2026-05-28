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
 * Подарунковий сертифікат
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificate extends Product {

    @Column(nullable = false)
    private String certificateType; // Тип (наприклад: "Електронний", "Пластикова картка")

    @Size(min = 1)
    private int validityMonths; // Термін дії у місяцях

    @Column(nullable = false, length = 100)
    private String termsOfUse; // Короткі умови використання (наприклад: "Діє на весь асортимент, крім акційних товарів")

    public GiftCertificate(Long id, String name, String description, BigDecimal price, BigDecimal discount,
                           MeasureUnit unit, int stockQuantity, boolean isAvailable, String imageUrl,
                           String article, String supplier, String brand, String country,
                           String certificateType, int validityMonths, String termsOfUse) {

        super(id, article, name, description, price, discount, unit,
                stockQuantity, isAvailable, imageUrl, null, supplier, brand, country);
        this.certificateType = certificateType;
        this.validityMonths = validityMonths;
        this.termsOfUse = termsOfUse;
    }

    @Override
    public String render(String indent) {
        return indent + "Подарунковий сертифікат: [" + super.getBaseDetails() +
                ", тип=" + certificateType +
                ", термін дії=" + validityMonths + " міс." +
                ", умови=" + termsOfUse + "]";
    }

    @Override
    public String renderName() {
        return "Подарунковий сертифікат, " + certificateType +
                ", номіналом " + getTotalPrice() + " грн, " + article;
    }
}
