package misha.bondarenko.entities.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Відгук на товар, набор, магазин в цілому тощо
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String text;

//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private User user;

    /**
     * Час створення відгуку
     */
    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Size(min = 1, max = 5)
    @Column(nullable = false)
    private int rating;
}
