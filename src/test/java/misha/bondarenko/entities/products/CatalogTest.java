package misha.bondarenko.entities.products;

import misha.bondarenko.enums.MeasureUnit;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CatalogTest {
    private final Catalog catalog = new Catalog();

    @Test
    @Order(1)
    void setName() {
        catalog.setName("Інструменти");
        assertEquals("Інструменти",  catalog.getName());
    }

    @Test
    @Order(2)
    void addProduct() {
        catalog.setName("blyat");
        catalog.add(
                Tool.builder().toolType("needle")
                        .material("metal").size("12mm").isAvailable(true).code("12412").article("3121")
                        .supplier("Mishaa").brand("Bondarenko").stockQuantity(19).unit(MeasureUnit.UNIT)
                        .price(new BigDecimal(200)).discount(new BigDecimal(0)).id(null).name("suka")
                        .description("huy ego znaet chto eto za pizda").build(),
                Tool.builder().toolType("hook")
                        .material("derevo").size("20mm").isAvailable(false).code("9812").article("49132")
                        .supplier("Mishaa").brand("Bondarenko").stockQuantity(109).unit(MeasureUnit.UNIT)
                        .price(new BigDecimal(5000)).discount(new BigDecimal("0.1")).id(null).name("Hook pizdatiy")
                        .description("huy ego znaet chto eto za pizda, opyat' zhe").build()
        );

        System.out.println(catalog.render(" "));
    }

    @Test
    @Order(3)
    void addCatalog() {
        catalog.setName("blyat");
        catalog.add(
                Tool.builder().toolType("needle")
                        .material("metal").size("12mm").isAvailable(true).code("12412").article("3121")
                        .supplier("Mishaa").brand("Bondarenko").stockQuantity(19).unit(MeasureUnit.UNIT)
                        .price(new BigDecimal(200)).discount(new BigDecimal(0)).id(null).name("suka")
                        .description("huy ego znaet chto eto za pizda").build(),
                Tool.builder().toolType("hook")
                        .material("derevo").size("20mm").isAvailable(false).code("9812").article("49132")
                        .supplier("Mishaa").brand("Bondarenko").stockQuantity(109).unit(MeasureUnit.UNIT)
                        .price(new BigDecimal(5000)).discount(new BigDecimal("0.1")).id(null).name("Hook pizdatiy")
                        .description("huy ego znaet chto eto za pizda, opyat' zhe").build()
        );
        catalog.add(
                new Catalog(null, "name", "something stupid")
        );
        catalog.getChild(2).add(
                Yarn.builder().fiberContent("sintepuh").lengthInMeters(20).weightInGrams(500)
                        .dyeLot("12412").color("light black").isAvailable(true).code("8731").article("091204")
                        .supplier("Mishaa").brand("Bondarenko").stockQuantity(9).unit(MeasureUnit.SQUIRM).price(new BigDecimal(45))
                        .discount(BigDecimal.ZERO).id(null).name("Synthopon zaebis").description("Ochen pizdatiy synthopon, recomenduy").build()
        );
        System.out.println(catalog.render(" "));
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