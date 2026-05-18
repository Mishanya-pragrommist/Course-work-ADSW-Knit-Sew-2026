package misha.bondarenko.entities.products;

import misha.bondarenko.enums.MeasureUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class KitTest {

    private Fabric linenFabric;
    private Fabric cottonFabric;
    private Equipment sewingMachine;

    private Kit winterKit;
    private Kit ecoKit;

    @BeforeEach
    void setUp() {
        linenFabric = new Fabric(
                true,
                "FAB-001",
                "ART-101",
                "Постачальник Текстиль",
                "BrandTextile",
                100,
                MeasureUnit.M,
                new BigDecimal("200.00"),
                new BigDecimal("0.10"), // 180.00 загалом
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
                MeasureUnit.M,
                new BigDecimal("100.00"),
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
                MeasureUnit.PIECE,
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

        // Ініціалізація наборів
        winterKit = new Kit(1L, "Зимовий набір", "Набір для зимового одягу",
                new BigDecimal("0.10")); // Знижка набору 10%
        winterKit.setName("Зимовий набір");

        ecoKit = new Kit(2L, "Еко набір",
                "Екологічно чисті матеріали", BigDecimal.ZERO); // Без знижки
        ecoKit.setName("Еко набір");

        // Наповнення наборів компонентами
        winterKit.add(linenFabric, sewingMachine); // Компоненти: 180.00 + 4750.00 = 4930.00
        ecoKit.add(cottonFabric, 2); // 2 штуки по 100 за кожну, загалом: 200.00
    }

    @Test
    @DisplayName("Розрахунок ціни набору без знижки")
    void testKitPriceWithoutDiscount() {
        System.out.println(ecoKit.render(""));
        assertThat(ecoKit.getPrice()).isEqualByComparingTo(new BigDecimal("200.00"));
    }

    @Test
    @DisplayName("Розрахунок ціни набору з урахуванням знижки набору та знижок товарів")
    void testKitPriceWithDiscount() {
        // Сума компонентів: 180.00 (льон зі знижкою) + 4750.00 (машина зі знижкою) = 4930.00
        // Знижка набору: 10%
        // Очікувана ціна: 4930.00 - (4930.00 * 0.10) = 4437.00
        BigDecimal expectedPrice = new BigDecimal("4437.00");
        assertThat(winterKit.getPrice()).isEqualByComparingTo(expectedPrice);
    }

    @Test
    @DisplayName("Додавання та видалення компонентів із набору")
    void testKitAddAndRemoveComponents() {
        winterKit.add(sewingMachine);
        System.out.println("Kit before removing\n" + winterKit.render(""));
        // Початкова кількість компонентів у winterKit = 2 (якщо рахувати тільки класи),
        // та 3, якщо рахувати к-сть товарів;
        // початкова ціна з урахуванням знижок: 8712.00
        assertThat(winterKit.getChild(0)).isEqualTo(linenFabric);
        assertThat(winterKit.getChild(1)).isEqualTo(sewingMachine);

        // Видаляємо льон, залишаються лише машини (ціна 8550.00 зі знижками)
        winterKit.remove(linenFabric);
        System.out.println("Kit after removing:\n" + winterKit.render(""));

        assertThat(winterKit.getPrice()).isEqualByComparingTo(new BigDecimal("8550.00"));
    }
}
