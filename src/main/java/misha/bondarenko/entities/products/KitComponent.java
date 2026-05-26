package misha.bondarenko.entities.products;

import jakarta.persistence.*;
import lombok.*;
import misha.bondarenko.entities.products.Item;

/**
 * Допоміжна сутність для збереження кількості конкретного товару в наборі
 */
@Entity
@Table(name = "kit_components")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KitComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private Product item;

    /**
     * Кількість товару item в наборі
     */
    @Column(nullable = false)
    private int quantity;
}