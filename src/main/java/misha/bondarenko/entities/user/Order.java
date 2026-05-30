package misha.bondarenko.entities.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import misha.bondarenko.entities.products.Product;
import misha.bondarenko.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Замовлення
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private Long id;

    @Column(nullable = false)
    private OrderStatus orderStatus = OrderStatus.NEW;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<OrderItem> orderItems;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private String addressSnapshot;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Column(nullable = false)
    private double totalWeight;

    @Column(nullable = false)
    private String ttnNumber;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;
}
