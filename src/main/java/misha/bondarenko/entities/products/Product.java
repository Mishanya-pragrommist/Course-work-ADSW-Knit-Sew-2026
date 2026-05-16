package misha.bondarenko.entities.products;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Товар
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Product extends Item {

    protected boolean isAvailable;

    @Column(nullable = false, unique = true)
    protected String code; // Код виду 010203

    @Column(nullable = false, unique = true)
    protected String article; // Артикул

    protected String supplier; // Постачальник
    protected String brand; // Бренд товару
    protected int stockQuantity; // Кількість на складі

    @Enumerated(EnumType.STRING)
    protected MeasureUnit unit; // Одиниця вимірювання кількості на складі

    protected BigDecimal price; // Ціна (без знижки)
    protected BigDecimal discount; // Знижка у форматі дробу (наприклад, 0.10 для 10%)

    public Product(boolean isAvailable,
                   String article,
                   String name,
                   String description,
                   String supplier,
                   String brand,
                   int stockQuantity,
                   MeasureUnit unit,
                   BigDecimal price,
                   BigDecimal discount) {
        this.isAvailable = isAvailable;
        this.code = "";
        this.article = article;
        this.name = name;
        this.description = description;
        this.supplier = supplier;
        this.brand = brand;
        this.stockQuantity = stockQuantity;
        this.unit = unit;
        this.price = price;
        this.discount = discount != null ? discount : BigDecimal.ZERO;
    }

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

    /**
     * Для дебагу
     * @return рядок
     */
    @Override
    public String toString() {
        return "Товар: [" + getBaseDetails() + "]";
    }

    /**
     * Для відображення товару у вигляді картки на сторінці вибору
     * @param indent тимчасове
     * @return відформатований рядок
     */
    public String render(String indent) {
        return indent + "Товар: [" + getBaseDetails() + "]";
    }
}