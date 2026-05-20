package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import misha.bondarenko.enums.MeasureUnit;

import java.math.BigDecimal;

/**
 * Схема або інструкція для в'язання/шиття
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Pattern extends Product {

    private String author; // Автор схеми
    private String difficultyLevel; // Рівень складності ("Початковий", "Середній", "Складний")
    private String language; // Мова інструкції ("Українська", "Англійська")
    private String format; // Формат ("PDF", "Друкований буклет")

    public Pattern(boolean isAvailable, String code, String article, String supplier, String brand,
                   int stockQuantity, MeasureUnit unit, BigDecimal price, BigDecimal discount,
                   String author, String difficultyLevel, String language, String format) {
        super(isAvailable, code, article, supplier, brand, stockQuantity, unit, price, discount);
        this.author = author;
        this.difficultyLevel = difficultyLevel;
        this.language = language;
        this.format = format;
    }

    @Override
    public String render(String indent) {
        return indent + "Схема/Інструкція: [" + super.getBaseDetails() +
                ", автор=" + author +
                ", складність=" + difficultyLevel +
                ", мова=" + language +
                ", формат=" + format + "]";
    }

    @Override
    public String renderName() {
        return name + " від автора" + author +
                difficultyLevel.toLowerCase() + "рівень, " + format;
    }

}