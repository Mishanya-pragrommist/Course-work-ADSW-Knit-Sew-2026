package misha.bondarenko.entities.products;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import misha.bondarenko.enums.MeasureUnit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Набір для рукоділля. Не може містити вкладені набори
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Kit extends Item {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "kit_id")
    private List<KitComponent> components = new ArrayList<>();

    public Kit(Long id, String article, String name, String description,
               BigDecimal discount, boolean isAvailable, String imageUrl) {

        super(id, article, name, description, discount, isAvailable, imageUrl, null);
    }

    /**
     * Кількість можливих динамічних наборів розраховується як мінімальна кількість доступних товарів
     */
    // TODO: fix this method because it always returns initial value of min
    @Override
    public int getStockQuantity() {
        if (components.isEmpty()) { return 0; }

        int min = 1;
        for (KitComponent component : components) {
            int currentMin = component.getItem().getStockQuantity();
            if (currentMin <= min) {
                min = currentMin;
            }
        }
        return min;
    }

    /**
     * Динамічно обчислює ціну всього набору.
     * Сумує (ціна_компонента * кількість) та застосовує знижку набору (якщо така задана)
     */
    @Override
    public BigDecimal getTotalPrice() {
        BigDecimal total = getBasePrice();

        if (discount != null && discount.compareTo(BigDecimal.ZERO) > 0) {
            return total.subtract(total.multiply(discount));
        }
        return total;
    }

    @Override
    public BigDecimal getBasePrice() {
        return components.stream()
                .map(comp -> comp.getItem().getTotalPrice()
                        .multiply(BigDecimal.valueOf(comp.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String renderName() {
        return name + " (" + components.size() + " комп.), " + article;
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
