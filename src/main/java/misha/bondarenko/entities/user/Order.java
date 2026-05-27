package misha.bondarenko.entities.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import misha.bondarenko.entities.products.Product;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Замовлення
 */
//@Entity
@Getter
@Setter
public class Order {
    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<Product> products;

    @Column(nullable = false)
    private LocalDateTime creationDate;
}
