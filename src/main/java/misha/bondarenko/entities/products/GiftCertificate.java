package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Подарунковий сертифікат
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
public class GiftCertificate extends Product {

    private String certificateType; // Тип (наприклад: "Електронний", "Пластикова картка")
    private int validityMonths; // Термін дії у місяцях
    private String termsOfUse; // Короткі умови використання (наприклад: "Діє на весь асортимент, крім акційних товарів")

    @Override
    public String render(String indent) {
        return indent + "Подарунковий сертифікат: [" + super.getBaseDetails() +
                ", тип=" + certificateType +
                ", термін дії=" + validityMonths + " міс." +
                ", умови=" + termsOfUse + "]";
    }
}