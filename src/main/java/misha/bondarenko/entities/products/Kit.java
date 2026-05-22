package misha.bondarenko.entities.products;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import misha.bondarenko.enums.MeasureUnit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Набір для рукоділля. Не може (і не має) містити вкладені набори
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Kit extends Item {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "kit_id")
    private List<KitComponent> components = new ArrayList<>();

    /** Додаткова знижка саме за купівлю набором */
    private BigDecimal kitDiscount = BigDecimal.ZERO;

    public Kit(Long id, String article, String name, String description,
               BigDecimal price, BigDecimal discount, MeasureUnit unit,
               int stockQuantity, boolean isAvailable, String imageUrl) {

        super(id, article, name, description, price, discount, unit,
                stockQuantity, isAvailable, imageUrl, null);
    }

    /**
     * Динамічно обчислює ціну всього набору.
     * Сумує (ціна_компонента * кількість) та застосовує знижку набору (якщо така задана)
     */
    @Override
    public BigDecimal getTotalPrice() {
        BigDecimal total = components.stream()
                .map(comp -> comp.getItem().getTotalPrice()
                .multiply(BigDecimal.valueOf(comp.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (kitDiscount != null && kitDiscount.compareTo(BigDecimal.ZERO) > 0) {
            return total.subtract(total.multiply(kitDiscount));
        }
        return total;
    }

    @Override
    public BigDecimal getPurePrice() {
        return getTotalPrice();
    }

    @Override
    public BigDecimal getDiscount() {
        return kitDiscount;
    }

    @Override
    public boolean isAvailable() {
        return this.isAvailable;
    }

    @Override
    public String renderName() {
        return "Набір \"" + name + "\" (" + components.size() + " комп.)";
    }

    /**
     * Додає товар до набору із зазначенням конкретної кількості.
     * Якщо такий товар уже є в наборі, кількість підсумовується.
     */
    public void add(Product item, int quantity) {
        for (KitComponent comp : components) {
            if (comp.getItem().equals(item)) {
                comp.setQuantity(comp.getQuantity() + quantity);
                return;
            }
        }
        components.add(new KitComponent(null, item, quantity));
    }

    /**
     * Додає товари з кількістю за замовчуванням (1 шт.).
     */
    public void add(Item... items) {
        for (Item item : items) {
            if (item instanceof Product) {
                add((Product) item, 1);
            }
        }
    }

    public void remove(Item item) {
        components.removeIf(comp -> comp.getItem().equals(item));
    }

    public Item getChild(int index) {
        if (index < 0 || index >= components.size()) {
            throw new IndexOutOfBoundsException("Елемент не знайдено");
        }
        return components.get(index).getItem();
    }

    @Override
    public String render(String indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent).append("Набір: ").append(name)
                .append(" | Загальна вартість: ").append(getTotalPrice()).append("\n");
        sb.append(indent).append("Склад:\n");
        for (KitComponent component : components) {
            sb.append(indent).append("  - ")
                    .append(component.getItem().getName())
                    .append(" x").append(component.getQuantity())
                    .append(" (").append(component.getItem().getTotalPrice()).append(" за шт.)\n");
        }
        return sb.toString();
    }

}
