//package misha.bondarenko.entities.products;
//
//import misha.bondarenko.enums.MeasureUnit;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//class CatalogTest {
//
//    private Category rootCatalog;
//    private Category fabricCategory;
//    private Category equipmentCategory;
//    private Category kitCategory;
//
//    private Fabric linenFabric;
//    private Fabric cottonFabric;
//    private Equipment sewingMachine;
//
//    private Kit winterKit;
//    private Kit ecoKit;
//
//    @BeforeEach
//    void setUp() {
//        rootCatalog = new Category(1L, "Головний каталог", "Кореневий каталог Knit&Sew", "http");
//        fabricCategory = new Category(2L, "Тканини", "Категорія тканин", "https://aura");
//        equipmentCategory = new Category(3L, "Обладнання", "Швейне та в'язальне обладнання", "https://aurayarns.");
//        kitCategory = new Category(4L, "Набори", "Готові набори для в'язання", "https://a");
//
//        linenFabric = new Fabric(
//                true,
//                "FAB-001",
//                "ART-101",
//                "Постачальник Текстиль",
//                "BrandTextile",
//                100,
//                MeasureUnit.METER,
//                new BigDecimal("200.00"), // Базова ціна
//                new BigDecimal("0.10"), // Знижка 10%. У результаті ціна має бути 180.00 грн
//                "Льон",
//                "100% льон",
//                150,
//                210,
//                "Натуральний"
//        );
//
//        linenFabric.setName("Натуральний льон");
//
//        cottonFabric = new Fabric(
//                true,
//                "FAB-002",
//                "ART-102",
//                "Постачальник Текстиль",
//                "BrandTextile",
//                150,
//                MeasureUnit.METER,
//                new BigDecimal("100.00"), // Базова ціна
//                BigDecimal.ZERO, // Без знижки
//                "Бавовна",
//                "100% бавовна",
//                140,
//                150,
//                "Синій"
//        );
//        cottonFabric.setName("Синя бавовна");
//
//        sewingMachine = new Equipment(
//                true,
//                "EQ-001",
//                "ART-201",
//                "Singer Corp",
//                "Singer",
//                10,
//                MeasureUnit.UNIT,
//                new BigDecimal("5000.00"),
//                new BigDecimal("0.05"), // 5% знижки (фінальна ціна: 4750.00)
//                "Швейна машина",
//                24,
//                85,
//                "40x30x20 см",
//                7.5,
//                23
//        );
//        sewingMachine.setName("Швейна машина Singer 23");
//
//        // Ініціалізація наборів (Kit) згідно з наданим тобою конструктором (Long id)
//        winterKit = new Kit(1L, "Зимовий набір",
//                "Набір для зимового одягу",  "https://aurayarns.r", new BigDecimal("0.10")); // Знижка набору 10%
//        winterKit.setName("Зимовий набір");
//
//        ecoKit = new Kit(2L, "Еко набір",
//                "Екологічно чисті матеріали", "alkjsdfklasdf",BigDecimal.ZERO); // Без додаткової знижки
//        ecoKit.setName("Еко набір");
//
//        // Наповнення наборів компонентами
//        winterKit.add(linenFabric, sewingMachine); // Компоненти: 180.00 + 4750.00 = 4930.00 - 10% = 4437.00
//        ecoKit.add(cottonFabric); // Компоненти: 100.00
//    }
//
//    @Test
//    @DisplayName("Додавання товарів, підкатегорій та наборів до каталогу")
//    void testAddItemsAndCategories() {
//        // Додаємо товари до відповідних підкатегорій
//        fabricCategory.add(linenFabric);
//        fabricCategory.add(cottonFabric);
//        equipmentCategory.add(sewingMachine);
//
//        kitCategory.add(winterKit, ecoKit);
//
//        System.out.println(rootCatalog.render(""));
//
//        // Перевіряємо структуру
//        assertThat(fabricCategory.getChildren()).hasSize(2).containsExactlyInAnyOrder(linenFabric, cottonFabric);
//        assertThat(equipmentCategory.getChildren()).hasSize(1).contains(sewingMachine);
//        assertThat(kitCategory.getChildren()).hasSize(2).contains(winterKit, ecoKit);
//    }
//
//    @Test
//    @DisplayName("Успішне видалення товарів та категорій з ієрархії")
//    void testRemoveItems() {
//        fabricCategory.add(linenFabric);
//        fabricCategory.add(cottonFabric);
//
//        assertThat(fabricCategory.getChildren()).hasSize(2);
//
//        fabricCategory.remove(linenFabric);
//
//        assertThat(fabricCategory.getChildren()).hasSize(1).containsOnly(cottonFabric);
//    }
//
//}
