package misha.bondarenko.entities.products;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Представляє категорію або каталог, що містить інші елементи (товари або підкаталоги).
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String imageUrl;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private final List<Item> children = new ArrayList<>();

    public Category(Long id, String name, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // --- Реалізація методів роботи з елементами каталогів ---

    public void add(Item... item) {
        List<Item> list = List.of(item);
        list.forEach(i -> i.setParent(this));
        this.children.addAll(list);
    }

    public void remove(Item item) {
        children.remove(item);
    }

    public Item getChild(int index) {
        if (index < 0 || index >= children.size()) {
            throw new IndexOutOfBoundsException("Елемент з індексом " + index + " не знайдено.");
        }
        return children.get(index);
    }


    /** Повертає список усіх дочірніх елементів для ітерації */
    public List<Item> getChildren() {
        return new ArrayList<>(children);
    }

    // TODO: decide if I should keep this method or delegate searching to services or something

    public String render(String indent) {
        StringBuilder sb = new StringBuilder(indent).append("+ ").append(name).append("\n");
        for (Item n : children) {
            sb.append(n.render(indent + "\t")).append("\n");
        }
        return sb.toString().stripTrailing();
    }

    public String renderName() {
        return name;
    }

}