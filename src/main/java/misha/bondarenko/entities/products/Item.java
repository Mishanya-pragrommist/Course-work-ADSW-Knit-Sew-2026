package misha.bondarenko.entities.products;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Базовий абстрактний клас для патерну Композит (Component).
 * Представляє загальний елемент системи: товар, набір або каталог.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected String name;
    protected String description;

    /**
     * Батьківський каталог
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Catalog parent;

    // --- Базові методи елемента ---

    /**
     * Отримання ціни елемента
     */
    public abstract BigDecimal getPrice();

    // --- Методи роботи з нащадками ---
    // За замовчуванням генерують виняток. Перевизначаються лише в Catalog та Kit.

    /**
     * Додавання елементу до колекції (каталогу або сету)
     * @param item об'єкт для додавання
     */
    public void add(Item... item) {
        throw new UnsupportedOperationException("Операція додавання не підтримується цим елементом.");
    }

    /**
     * Видалення елементу з колекції (каталогу або сету)
     * @param item об'єкт для видалення
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

    /**
     * Метод для відображення товарів та категорій у вигляді дерева
     * @param indent відступ
     * @return відформатований рядок
     */
    public abstract String render(String indent);

    /**
     * Для відображення повної назви товару, категорії або набору.<br>
     * Для товарів назва може виглядати як "Пряжа синя Alize 100г Акрил",
     * тобто як сукупність певних параметрів.<br>
     * Для категорій назва - назва та опис<br>
     * Для наборів товарів - назва виду "Набір для в'язання шарфу"
     * @return рядок з повною назвою товару
     */
    public abstract String renderName();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;
        return id.equals(item.id) && name.equals(item.name) && description.equals(item.description);
    }
}
