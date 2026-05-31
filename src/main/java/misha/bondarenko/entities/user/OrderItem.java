package misha.bondarenko.entities.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Містить копії полів з товарів для збереження в історії покупок.
 * Завдяки цьому якщо дані про цей товар зміняться (ціна, назва, артикул),
 * в історії все одно будуть дані на момент створення замовлення
 */
//@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemName = "";

    @Column(nullable = false)
    private String itemArticle = "";

    @Size(min = 1)
    private int quantity = 1;

    @Column(nullable = false)
    private BigDecimal unitPrice = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal discount = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal totalPrice = BigDecimal.ZERO;
}
