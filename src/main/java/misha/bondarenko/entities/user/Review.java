package misha.bondarenko.entities.user;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Відгук на товар, набор, магазин в цілому тощо
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review {

    private String text;



}
