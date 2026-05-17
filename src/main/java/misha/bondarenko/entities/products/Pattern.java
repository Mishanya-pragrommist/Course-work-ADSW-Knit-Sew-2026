package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Схема або інструкція для в'язання/шиття
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
public class Pattern extends Product {

    private String author; // Автор схеми
    private String difficultyLevel; // Рівень складності ("Початковий", "Середній", "Складний")
    private String language; // Мова інструкції ("Українська", "Англійська")
    private String format; // Формат ("PDF", "Друкований буклет")

    @Override
    public String render(String indent) {
        return indent + "Схема/Інструкція: [" + super.getBaseDetails() +
                ", автор=" + author +
                ", складність=" + difficultyLevel +
                ", мова=" + language +
                ", формат=" + format + "]";
    }
}