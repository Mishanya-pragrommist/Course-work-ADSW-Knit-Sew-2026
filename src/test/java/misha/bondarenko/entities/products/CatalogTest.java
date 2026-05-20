package misha.bondarenko.entities.products;

import misha.bondarenko.enums.MeasureUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CatalogTest {

    private Catalog rootCatalog;
    private Catalog fabricCategory;
    private Catalog equipmentCategory;
    private Catalog kitCategory;

    private Fabric linenFabric;
    private Fabric cottonFabric;
    private Equipment sewingMachine;

    private Kit winterKit;
    private Kit ecoKit;

    @BeforeEach
    void setUp() {
        rootCatalog = new Catalog(1L, "Головний каталог", "Кореневий каталог Knit&Sew", "https://aurayarns.rua-bivaet-pr");
        fabricCategory = new Catalog(2L, "Тканини", "Категорія тканин", "https://aurayarns.ru/tpost/g1fclx83");
        equipmentCategory = new Catalog(3L, "Обладнання", "Швейне та в'язальне обладнання", "https://aurayarns.");
        kitCategory = new Catalog(4L, "Набори", "Готові набори для в'язання", "https://aurayarns.ru/tp");

        linenFabric = new Fabric(
                true,
                "FAB-001",
                "ART-101",
                "Постачальник Текстиль",
                "BrandTextile",
                100,
                MeasureUnit.METERS,
                new BigDecimal("200.00"), // Базова ціна
                new BigDecimal("0.10"), // Знижка 10%. У результаті ціна має бути 180.00 грн
                "Льон",
                "100% льон",
                150,
                210,
                "Натуральний"
        );

        linenFabric.setName("Натуральний льон");

        cottonFabric = new Fabric(
                true,
                "FAB-002",
                "ART-102",
                "Постачальник Текстиль",
                "BrandTextile",
                150,
                MeasureUnit.METERS,
                new BigDecimal("100.00"), // Базова ціна
                BigDecimal.ZERO, // Без знижки
                "Бавовна",
                "100% бавовна",
                140,
                150,
                "Синій"
        );
        cottonFabric.setName("Синя бавовна");

        sewingMachine = new Equipment(
                true,
                "EQ-001",
                "ART-201",
                "Singer Corp",
                "Singer",
                10,
                MeasureUnit.UNIT,
                new BigDecimal("5000.00"),
                new BigDecimal("0.05"), // 5% знижки (фінальна ціна: 4750.00)
                "Швейна машина",
                24,
                85,
                "40x30x20 см",
                7.5,
                23
        );
        sewingMachine.setName("Швейна машина Singer 23");

        // Ініціалізація наборів (Kit) згідно з наданим тобою конструктором (Long id)
        winterKit = new Kit(1L, "Зимовий набір", "Набір для зимового одягу", "https://aurayarns.r", new BigDecimal("0.10")); // Знижка набору 10%
        winterKit.setName("Зимовий набір");

        ecoKit = new Kit(2L, "Еко набір",
                "Екологічно чисті матеріали", "alkjsdfklasdf",BigDecimal.ZERO); // Без додаткової знижки
        ecoKit.setName("Еко набір");

        // Наповнення наборів компонентами
        winterKit.add(linenFabric, sewingMachine); // Компоненти: 180.00 + 4750.00 = 4930.00 - 10% = 4437.00
        ecoKit.add(cottonFabric); // Компоненти: 100.00
    }

    @Test
    @DisplayName("Додавання товарів, підкатегорій та наборів до каталогу")
    void testAddItemsAndCategories() {
        // Додаємо підкатегорії до головного каталогу
        rootCatalog.add(fabricCategory);
        rootCatalog.add(equipmentCategory);
        rootCatalog.add(kitCategory);

        // Додаємо товари до відповідних підкатегорій
        fabricCategory.add(linenFabric);
        fabricCategory.add(cottonFabric);
        equipmentCategory.add(sewingMachine);

        kitCategory.add(winterKit, ecoKit);

        System.out.println(rootCatalog.render(""));

        // Перевіряємо структуру
        assertThat(rootCatalog.getChildren()).hasSize(3);
        assertThat(fabricCategory.getChildren()).hasSize(2).containsExactlyInAnyOrder(linenFabric, cottonFabric);
        assertThat(equipmentCategory.getChildren()).hasSize(1).contains(sewingMachine);
        assertThat(kitCategory.getChildren()).hasSize(2).contains(winterKit, ecoKit);
    }

    @Test
    @DisplayName("Комплексний розрахунок вартості: Каталог із категоріями та наборами")
    void testComplexCatalogPriceCalculation() {
        // Збираємо повне дерево
        rootCatalog.add(fabricCategory);
        rootCatalog.add(equipmentCategory);
        rootCatalog.add(winterKit); // Набір напряму в корені (4437.00)

        fabricCategory.add(cottonFabric); // Звичайна категорія (100.00)
        equipmentCategory.add(sewingMachine); // Звичайна категорія (4750.00)

        // Загальна ціна: 4437.00 (winterKit) + 100.00 (cotton) + 4750.00 (machine) = 9287.00
        BigDecimal expectedTotalPrice = new BigDecimal("9287.00");
        assertThat(rootCatalog.getPrice()).isEqualByComparingTo(expectedTotalPrice);
    }

    @Test
    @DisplayName("Пошук товарів та категорій за назвою у всій ієрархії")
    void testSearchInHierarchy() {
        // Збираємо структуру
        rootCatalog.add(fabricCategory);
        rootCatalog.add(equipmentCategory);
        rootCatalog.add(kitCategory);

        fabricCategory.add(linenFabric);
        fabricCategory.add(cottonFabric);
        equipmentCategory.add(sewingMachine);

        kitCategory.add(winterKit);

        System.out.println(rootCatalog.render(""));

        // Шукаємо за ключовим словом "льон"
        List<Item> searchResults = rootCatalog.searchRecursively("льон");

        assertThat(searchResults).hasSize(1);
        assertThat(searchResults.get(0).getName()).isEqualTo("Натуральний льон");

        List<Item> categoryResults = rootCatalog.searchRecursively("Обладнання");
        List<Item> kitResults = rootCatalog.searchRecursively(kitCategory.getName());

        System.out.println();
        assertThat(categoryResults).hasSize(1);
        assertThat(categoryResults.get(0)).isInstanceOf(Catalog.class);
        assertThat(kitResults).hasSize(1);
    }

    @Test
    @DisplayName("Успішне видалення товарів та категорій з ієрархії")
    void testRemoveItems() {
        rootCatalog.add(fabricCategory);
        fabricCategory.add(linenFabric);
        fabricCategory.add(cottonFabric);

        assertThat(fabricCategory.getChildren()).hasSize(2);

        fabricCategory.remove(linenFabric);

        assertThat(fabricCategory.getChildren()).hasSize(1).containsOnly(cottonFabric);
        assertThat(fabricCategory.getPrice()).isEqualByComparingTo(new BigDecimal("100.00"));
    }

    @Test
    @DisplayName("Спроба виконати операції управління над товаром " +
            "за допомогою об'єкту товару має викликати UnsupportedOperationException")
    void testLeafUnsupportedOperations() {
        assertThatThrownBy(() -> linenFabric.add(cottonFabric))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("не підтримується");

        assertThatThrownBy(() -> linenFabric.remove(cottonFabric))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("не підтримується");
    }

}
