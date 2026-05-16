package misha.bondarenko.entities.products;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Базовий абстрактний клас для патерну Композит (Component).
 * Представляє загальний елемент системи: товар, набір або каталог.
 */
@Entity
@Inheritance
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Item {

    @Id
    private Long id;
    protected String name;
    protected String description;

    // --- Базові методи елемента ---

    /**
     * Отримання ціни елемента.
     */
    public abstract BigDecimal getPrice();

    // --- Методи управління нащадками (Composite operations) ---
    // За замовчуванням генерують виняток. Перевизначаються лише в Catalog та Kit.

    /**
     * Додавання елементу до колекції (каталогу або сету)
     * @param item об'єкт для додавання
     */
    public void add(Item item) {
        throw new UnsupportedOperationException("Операція додавання не підтримується цим елементом.");
    }

    /**
     * Видалення елементу з колекції (каталогу або сету)
     * @param item об'єкт  для видалення
     */
    public void remove(Item item) {
        throw new UnsupportedOperationException("Операція видалення не підтримується цим елементом.");
    }

    /**
     * Отримання дочірнього елемента з дерева
     * @param index номер елемента в списку
     * @return знайдений елемент
     */
    public Item getChild(int index) {
        throw new UnsupportedOperationException("Операція отримання дочірнього елемента не підтримується цим елементом.");
    }
}
