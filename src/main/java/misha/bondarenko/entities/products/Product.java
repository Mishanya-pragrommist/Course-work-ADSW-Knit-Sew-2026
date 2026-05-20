package misha.bondarenko.entities.products;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

import misha.bondarenko.enums.MeasureUnit;


/**
 * Товар
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product extends Item {

    protected boolean isAvailable;

    @Column(nullable = false, unique = true)
    protected String code; // Код виду 010203

    @Column(nullable = false)
    protected String article; // Артикул

    protected String supplier; // Постачальник
    protected String brand; // Бренд товару
    protected int stockQuantity; // Кількість на складі

    @Enumerated(EnumType.STRING)
    protected MeasureUnit unit; // Одиниця вимірювання кількості на складі (поштучно, в метрах, в грамах тощо)

    protected BigDecimal price; // Ціна (без знижки)
    protected BigDecimal discount; // Знижка у форматі дробу "0.10"

    @Override
    public BigDecimal getPrice() {
        if (discount == null || discount.compareTo(BigDecimal.ZERO) == 0) {
            return price;
        }
        return price.subtract(price.multiply(discount));
    }

    protected String getBaseDetails() {
        return  "код=" + getCode() +
                ", артикул=" + article +
                ", назва=" + name +
                ", опис=" + description +
                ", постачальник=" + supplier +
                ", бренд=" + brand +
                ", кількість=" + stockQuantity +
                ", одиниці вимірювання=" + unit.getDescription() +
                ", загальна ціна=" + getPrice() +
                ", знижка=" + discount +
                ", доступність=" + isAvailable;
    }

    // TODO: adapt method to render products containment to HTML blocks
    /**
     * Для відображення товару у вигляді картки на сторінці вибору
     * @param indent тимчасове
     * @return відформатований рядок
     */
    public String render(String indent) {
        return indent + "Товар: [" +
                getBaseDetails() +
                "]";
    }
}