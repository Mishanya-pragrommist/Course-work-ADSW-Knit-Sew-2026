package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    public Book(Long id, String name, String description, BigDecimal price, BigDecimal discount,
                MeasureUnit unit, int stockQuantity, boolean isAvailable, String imageUrl,
                String article, String supplier, String brand, String country,
                String author, String publisher, String isbn, int pages, int publicationYear) {
        super(id, article, name, description, price, discount, unit,
                stockQuantity, isAvailable, imageUrl, null, supplier, brand, country);
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
        return "Книга \"" + name + "\" - " + author + ", видавництво " +
                publisher + ", " + publicationYear + " р." + ", " + article;
    }
}

