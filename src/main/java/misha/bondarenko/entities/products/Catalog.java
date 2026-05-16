package misha.bondarenko.entities.products;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Клас-композит (Composite).
 * Представляє категорію або каталог, що містить інші елементи (товари або підкаталоги).
 */
public class Catalog extends Item {

    private final List<Item> children = new ArrayList<>();

    public Catalog(Long id, String name, String description) {
        super(id, name, description);
    }

    /**
     * Обчислює загальну вартість усіх елементів у цьому каталозі.
     */
    @Override
    public BigDecimal getPrice() {
        return children.stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // --- Реалізація методів роботи з нащадками ---

    @Override
    public void add(Item item) {
        children.add(item);
    }

    @Override
    public void remove(Item item) {
        children.remove(item);
    }

    @Override
    public Item getChild(int index) {
        if (index < 0 || index >= children.size()) {
            throw new IndexOutOfBoundsException("Елемент з індексом " + index + " не знайдено.");
        }
        return children.get(index);
    }

    /**
     * Повертає список усіх дочірніх елементів для ітерації.
     */
    public List<Item> getChildren() {
        return new ArrayList<>(children);
    }
}