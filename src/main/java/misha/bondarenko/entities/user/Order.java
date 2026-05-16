package misha.bondarenko.entities.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
}
