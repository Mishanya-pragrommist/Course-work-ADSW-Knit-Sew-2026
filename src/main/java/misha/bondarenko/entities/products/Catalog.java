package misha.bondarenko.entities.products;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Представляє категорію або каталог, що містить інші елементи (товари або підкаталоги).
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Catalog extends Item {

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private final List<Item> children = new ArrayList<>();

    public Catalog(Long id, String name, String description, String imageUrl) {
        super(id, name, description, imageUrl, null);
    }

    // TODO: maybe delete this later
    /**
     * Обчислює загальну вартість усіх елементів у цьому каталозі
     * (нафіга це тут треба?)
     */
    @Override
    public BigDecimal getPrice() {
        return children.stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // --- Реалізація методів роботи з нащадками ---

    @Override
    public void add(Item... item) {
        List<Item> list = List.of(item);
        list.forEach(i -> i.setParent(this));
        this.children.addAll(list);
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

    // TODO: implement get subcatalogs only on current level. For example:
    // + catalog1
    //      + catalog11
    //      + catalog12
    //      + catalog13
    //          +catalog131
    //
    // catalog1.getSubCatalogs() should return catalog11, 12, 13 and not catalog131
    public List<Catalog> getSubCatalogs() {
        List<Catalog> catalogs = new ArrayList<>();
        for (Item item : children) {
            if (item instanceof Catalog) {
                catalogs.add((Catalog) item);
            }
        }
        return catalogs;
    }

    /**
     * Повертає список усіх дочірніх елементів для ітерації.
     */
    public List<Item> getChildren() {
        return new ArrayList<>(children);
    }

    // TODO: decide if I should keep this method or delegate searching to services or something

    /**
     * Метод пошуку в ієрархії каталогу за іменем
     */
    public List<Item> searchRecursively(String query) {
        List<Item> found = new ArrayList<>();

        for (Item item : children) {
            if (item.renderName().toLowerCase().contains(query.toLowerCase())) {
                found.add(item);
            }
            else if (item instanceof Catalog) {
                found.addAll(((Catalog) item).searchRecursively(query));
            }
        }
        return found;
    }


    @Override
    public String render(String indent) {
        StringBuilder sb = new StringBuilder(indent).append("+ ").append(name).append("\n");
        for (Item n : children) {
            sb.append(n.render(indent + "\t")).append("\n");
        }
        return sb.toString().stripTrailing();
    }

    @Override
    public String renderName() {
        return name;
    }

    // TODO: create a method to render catalog containment to HTML block

    public String renderHtmlHomePage() {
        return "renderHtml needs to be implemented";
    }

    /**
     * Для відображення категорій у вигляді стовпця зліва на домашній сторінці
     * @return форматований рядок (може, це буде список рядків, хз)
     */
    public String renderHtmlColumn() {
        return "renderHtmlColumn needs to be implemented";
    }
}