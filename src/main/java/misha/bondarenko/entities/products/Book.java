package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import misha.bondarenko.enums.MeasureUnit;

import java.math.BigDecimal;

/**
 * Книги та друковані журнали з рукоділля
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book extends Product {

    private String author; // Автор
    private String publisher; // Видавництво
    private String isbn; // Унікальний номер книги (ISBN)
    private int pages; // Кількість сторінок
    private int publicationYear; // Рік видання

    public Book(boolean isAvailable,
                String code,
                String article,
                String supplier,
                String brand,
                int stockQuantity,
                MeasureUnit unit,
                BigDecimal price,
                BigDecimal discount,
                String author,
                String publisher,
                String isbn,
                int pages,
                int publicationYear) {
        super(isAvailable, code, article, supplier, brand, stockQuantity, unit, price, discount);
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.pages = pages;
        this.publicationYear = publicationYear;
    }

    @Override
    public String render(String indent) {
        return indent + "Книга: [" + super.getBaseDetails() +
                ", автор=" + author +
                ", видавництво=" + publisher +
                ", ISBN=" + isbn +
                ", сторінок=" + pages +
                ", рік=" + publicationYear + "]";
    }

    @Override
    public String renderName() {
        return "Книга \"" + name + "\" - " + author + " " + publisher + ", " + publicationYear + " р.";
    }
}

