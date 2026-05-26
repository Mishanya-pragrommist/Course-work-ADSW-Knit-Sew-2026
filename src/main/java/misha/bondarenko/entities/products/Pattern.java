package misha.bondarenko.entities.products;

import jakarta.persistence.Column;
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

    @Column(nullable = false)
    private String difficultyLevel; // Рівень складності ("Початковий", "Середній", "Складний")

    @Column(nullable = false)
    private String language; // Мова інструкції ("Українська", "Англійська")

    @Column(nullable = false)
    private String format; // Формат ("PDF", "Друкований буклет")

    public Pattern(Long id, String name, String description, BigDecimal price, BigDecimal discount,
                   MeasureUnit unit, int stockQuantity, boolean isAvailable, String imageUrl,
                   String article, String supplier, String brand, String country,
                   String author, String difficultyLevel, String language, String format) {
        super(id, article, name, description, price, discount, unit,
                stockQuantity, isAvailable, imageUrl, null, supplier, brand, country);
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
        return name + " від автора " + author + ", " +
                difficultyLevel.toLowerCase() + " рівень, " + format + ", " + article;
    }

}