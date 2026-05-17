package misha.bondarenko.entities.products;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Набір для рукоділля. На відміну від каталогу,
 * не може містити вкладені каталоги чи набори
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Kit extends Item {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "kit_components",
            joinColumns = @JoinColumn(name = "kit_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> components = new ArrayList<>();

    // Додаткова знижка саме за купівлю набором (опціонально)
    private BigDecimal kitDiscount = BigDecimal.ZERO;

    public Kit(Long id, String name, String description, BigDecimal kitDiscount) {
        super(id, name, description);
        if (kitDiscount != null) {
            this.kitDiscount = kitDiscount;
        }
    }

    /**
     * Динамічно обчислює ціну набору як суму цін усіх компонентів
     * з урахуванням знижки на сам набір.
     */
    @Override
    public BigDecimal getPrice() {
        BigDecimal total = components.stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (kitDiscount != null && kitDiscount.compareTo(BigDecimal.ZERO) > 0) {
            return total.subtract(total.multiply(kitDiscount));
        }
        return total;
    }

    @Override
    public void add(Item item) {
        components.add(item);
    }

    @Override
    public void remove(Item item) {
        components.remove(item);
    }

    @Override
    public Item getChild(int index) {
        if (index < 0 || index >= components.size()) {
            throw new IndexOutOfBoundsException("Елемент не знайдено");
        }
        return components.get(index);
    }

    // Допоміжний метод для виводу складу набору
    public String render(String indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent).append("Набір: ").append(name)
                .append(" | Загальна вартість: ").append(getPrice()).append("\n");
        sb.append(indent).append("Склад:\n");
        for (Item component : components) {
            sb.append(indent).append("  - ").append(component.getName())
                    .append(" (").append(component.getPrice()).append(")\n");
        }
        return sb.toString();
    }
}
