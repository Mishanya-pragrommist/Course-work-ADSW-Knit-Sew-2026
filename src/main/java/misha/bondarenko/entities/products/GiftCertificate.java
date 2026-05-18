package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
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

    private String certificateType; // Тип (наприклад: "Електронний", "Пластикова картка")
    private int validityMonths; // Термін дії у місяцях
    private String termsOfUse; // Короткі умови використання (наприклад: "Діє на весь асортимент, крім акційних товарів")

    public GiftCertificate(boolean isAvailable, String code, String article, String supplier, String brand, int stockQuantity, MeasureUnit unit, BigDecimal price, BigDecimal discount, String certificateType, int validityMonths, String termsOfUse) {
        super(isAvailable, code, article, supplier, brand, stockQuantity, unit, price, discount);
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
}