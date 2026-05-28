package misha.bondarenko.entities.products;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import misha.bondarenko.enums.MeasureUnit;

import java.math.BigDecimal;

/**
 * Базовий абстрактний клас
 * Представляє товар та набір
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
    protected Long id;

    @Column(nullable = false)
    protected String article;      // Артикул товару або набору

    protected String name;         // Назва товару
    protected String description;  // Опис товару

    @Column(nullable = false)
    protected BigDecimal discount; // Знижка

    protected boolean isAvailable; // Наявність товару

    protected String imageUrl;     // Посилання на зображення

    /** Батьківський каталог */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    protected Catalog parent;

    // ============ Базові методи товару ============

    /** Отримання ціни елемента з урахуванням знижки */
    public abstract BigDecimal getTotalPrice();

    /** Отримання ціни елемента без знижки */
    public abstract BigDecimal getBasePrice();

    /**
     * Для відображення повної назви товару, категорії або набору.<br>
     * Для товарів назва може виглядати як "Пряжа синя Alize 100г Акрил",
     * тобто як сукупність певних параметрів.<br>
     * Для категорій назва - назва<br>
     * Для наборів товарів - назва виду "Набір для в'язання шарфу"
     * @return рядок з повною назвою товару
     */
    public abstract String renderName();

    public abstract String render(String indent);
}
