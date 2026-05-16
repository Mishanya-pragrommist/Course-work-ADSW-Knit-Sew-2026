package misha.bondarenko.entities.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import misha.bondarenko.entities.products.Product;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Кошик
 */
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    private Long id;

    private ArrayList<Product> products;

    public Cart(ArrayList<Product> products) {
        this.products = products;
    }

    /**
     * Отримати загальну вартість товарів в кошику
     * @return загальна вартість з урахуванням знижок
     */
    public BigDecimal getTotalPrice() {
        return products.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Додати товар до кошика
     * @param product товар
     */
    public void addProduct(Product product) {
        products.add(product);
    }

    /**
     * Видалити товар з кошика
     * @param product товар для видалення
     */
    public void removeProduct(Product product) {
        products.remove(product);
    }


}
