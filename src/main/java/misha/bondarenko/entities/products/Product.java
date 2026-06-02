package misha.bondarenko.entities.products;

import jakarta.persistence.*;
import lombok.*;
import misha.bondarenko.enums.MeasureUnit;

import java.math.BigDecimal;

/**
 * Товар (типу Пряжа, Тканина, Сертифікат, Набір тощо)
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends Item {

    @Column(nullable = false)
    protected BigDecimal basePrice;    // Ціна без знижки

    @Column(nullable = false)
    protected String supplier; // Постачальник

    @Column(nullable = false)
    protected String brand;    // Бренд товару

    @Column(nullable = false)
    protected String country;  // Країна виробник

    @Enumerated(EnumType.STRING)
    protected MeasureUnit unit;    // Одиниця вимірювання кількості на складі (поштучно, в метрах, в грамах тощо)

    protected int stockQuantity;   // Кількість на складі

    public Product(Long id,
                   String article,
                   String name,
                   String description,
                   BigDecimal basePrice,
                   BigDecimal discount,
                   MeasureUnit unit,
                   int stockQuantity,
                   boolean isAvailable,
                   String imageUrl,
                   Category parent,
                   String supplier,
                   String brand,
                   String country) {
        super(id, article, name, description, discount, isAvailable, imageUrl, parent);
        this.unit = unit;
        this.stockQuantity = stockQuantity;
        this.basePrice = basePrice;
        this.supplier = supplier;
        this.brand = brand;
        this.country = country;
    }

    /**
     * Якщо кількість на складі нульова, автоматично встановити доступність як false.
     * При цьому не слід змінювати доступність товару при зміненні кількості на складі,
     * оскільки товар може бути, наприклад, виключеним з продажу
     * @param stockQuantity кількість на складі
     */
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
        if (stockQuantity == 0) {
            this.isAvailable = false;
        }
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
    }

    @Override
    public int getStockQuantity() {
        return stockQuantity;
    }

    @Override
    public BigDecimal getTotalPrice() {
        if (discount == null || discount.compareTo(BigDecimal.ZERO) == 0) {
            return basePrice;
        }
        return basePrice.subtract(basePrice.multiply(discount));
    }

    protected String getBaseDetails() {
        return "артикул=" + article +
                ", назва=" + name +
                ", опис=" + description +
                ", постачальник=" + supplier +
                ", бренд=" + brand +
                ", кількість=" + stockQuantity +
                ", одиниці вимірювання=" + unit.getDescription() +
                ", загальна ціна=" + getTotalPrice() +
                ", знижка=" + discount +
                ", доступність=" + isAvailable;
    }

    /**
     * Для відображення товару у вигляді картки на сторінці вибору. ТИМЧАСОВЕ РІШЕННЯ ДЛЯ ДЕБАГУ
     * @param indent відступ
     * @return відформатований рядок
     */
    public String render(String indent) {
        return indent + "Товар: [" +
                getBaseDetails() +
                "]";
    }

    @Override
    public String renderName() {
        return "Товар " + name + " " + brand + " " + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return  name.equalsIgnoreCase(product.name)
                && discount.equals(product.discount)
                && supplier.equalsIgnoreCase(product.supplier)
                && brand.equalsIgnoreCase(product.brand)
                && stockQuantity == product.stockQuantity
                && unit.equals(product.unit)
                && basePrice.equals(product.basePrice)
                && isAvailable == product.isAvailable;
    }
}