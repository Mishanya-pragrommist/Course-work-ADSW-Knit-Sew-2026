package misha.bondarenko.entities.products;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KitTest {

    private Yarn testYarn;
    private Tool testTool;

    @BeforeEach
    void setUp() {
        testYarn = TestKitFactory.createYarnBordova(); // Ціна: 120.00
        testTool = TestKitFactory.createToolCircularSpokes(); // Ціна: 250.00
    }

    @Test
    @DisplayName("1. Додавання/видалення товарів та динамічне оновлення вартості набору")
    void testAddRemoveComponentsAndPriceUpdates() {
        // Створюємо порожній набір без знижки
        Kit dynamicKit = new Kit(null, "ART-DYNAMIC", "Динамічний набір",
                "Опис", BigDecimal.ZERO, true, "/images/plugs/kit.png");

        // Спочатку ціна має бути 0
        assertEquals(0, BigDecimal.ZERO.compareTo(dynamicKit.getTotalPrice()),
                "Порожній набір має коштувати 0");

        // 1. Додаємо 2 мотки пряжі (2 * 120.00 = 240.00)
        dynamicKit.add(testYarn, 2);
        BigDecimal expectedPriceAfterAdd = new BigDecimal("240.00");
        assertEquals(0, expectedPriceAfterAdd.compareTo(dynamicKit.getTotalPrice()),
                "Ціна набору має оновитися після додавання пряжі");

        // 2. Додаємо 1 інструмент (+ 250.00 = 490.00)
        dynamicKit.add(testTool, 1);
        BigDecimal expectedPriceAfterSecondAdd = new BigDecimal("490.00");
        assertEquals(0, expectedPriceAfterSecondAdd.compareTo(dynamicKit.getTotalPrice()),
                "Ціна набору має враховувати доданий інструмент");
        dynamicKit.remove(testTool);

        assertEquals(0, expectedPriceAfterAdd.compareTo(dynamicKit.getTotalPrice()),
                "Ціна набору має зменшитися після видалення інструменту");
    }

    @Test
    @DisplayName("2. Розрахунок вартості набору без знижки")
    void testKitPriceCalculationWithoutDiscount() {
        // Набір 'Старт в'язання' не має знижки
        // Склад: 3 мотки пряжі (3 * 120.00 = 360.00) + 1 спиці (250.00) = 610.00
        Kit kitWithoutDiscount = TestKitFactory.createKitStartKnitting();

        BigDecimal expectedTotal = new BigDecimal("610.00");
        assertEquals(0, expectedTotal.compareTo(kitWithoutDiscount.getTotalPrice()),
                "Розрахунок вартості набору без знижки виконано некоректно");
    }

    @Test
    @DisplayName("3. Розрахунок вартості набору з урахуванням системної знижки")
    void testKitPriceCalculationWithDiscount() {
        // Набір 'Швачка-початківець' має знижку 10% (0.10)
        // Склад: 2 котушки чорні (2 * 75.00) + 2 білі (2 * 75.00) = 300.00 грн
        // Знижка: 300.00 * 0.10 = 30.00 грн
        // Ціна зі знижкою: 300.00 - 30.00 = 270.00 грн
        Kit kitWithDiscount = TestKitFactory.createKitBeginnerSeamstress();

        BigDecimal expectedTotalWithDiscount = new BigDecimal("270.00");
        assertEquals(0, expectedTotalWithDiscount.compareTo(kitWithDiscount.getTotalPrice()),
                "Алгоритм застосування знижки на набір працює неправильно");
    }

    @Test
    @DisplayName("4. Тест методу renderName для генерації повної торгової назви")
    void testRenderNameFormatting() {
        Kit kit = TestKitFactory.createKitStartKnitting();
        String expectedName = "Набір 'Старт в'язання' (2 комп.), ART-1301";
        assertEquals(expectedName, kit.renderName());
    }
}
