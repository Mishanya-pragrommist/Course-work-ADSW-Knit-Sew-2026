package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Набір для рукоділля. На відміну від каталогу,
 * не може містити вкладені каталоги чи набори
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Kit extends Item {

    private BigDecimal price;
    private ArrayList<Product> products;


    @Override
    public BigDecimal getPrice() {
        return products.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
