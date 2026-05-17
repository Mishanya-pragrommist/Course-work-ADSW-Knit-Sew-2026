package misha.bondarenko.entities.products;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Книги та друковані журнали з рукоділля
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
public class Book extends Product {

    private String author; // Автор
    private String publisher; // Видавництво
    private String isbn; // Унікальний номер книги (ISBN)
    private int pages; // Кількість сторінок
    private int publicationYear; // Рік видання

    @Override
    public String render(String indent) {
        return indent + "Книга: [" + super.getBaseDetails() +
                ", автор=" + author +
                ", видавництво=" + publisher +
                ", ISBN=" + isbn +
                ", сторінок=" + pages +
                ", рік=" + publicationYear + "]";
    }
}

