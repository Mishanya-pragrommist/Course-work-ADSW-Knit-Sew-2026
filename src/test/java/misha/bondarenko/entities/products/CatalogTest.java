package misha.bondarenko.entities.products;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CatalogTest {
    private final Catalog catalog = new Catalog();

    @Test
    @Order(1)
    void setName() {
        catalog.setName("Інструменти");
    }

    @Test
    @Order(2)
    void addProduct() {
        catalog.add(
                new Tool(),
                new Tool(),
                new Tool()
        );
    }

    @Test
    @Order(3)
    void addCatalog() {

    }

    @Test
    void getPrice() {

    }

    @Test
    void remove() {
    }

    @Test
    void getChild() {
    }

    @Test
    void getChildren() {
    }
}