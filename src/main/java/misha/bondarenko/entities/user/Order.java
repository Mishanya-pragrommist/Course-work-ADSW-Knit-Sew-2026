package misha.bondarenko.entities.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import misha.bondarenko.entities.products.Product;

import java.util.List;

/**
 * Замовлення
 */
@Entity
@Getter
@Setter
public class Order {
    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    private List<Product> products;
}
