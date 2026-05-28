package misha.bondarenko.entities.products;

import misha.bondarenko.enums.MeasureUnit;
import java.math.BigDecimal;

/**
 * Фабрика тестових об'єктів та наборів (Kits) для інтеграційних та юніт-тестів.
 * Дані повністю синхронізовані з поточною версією DbFiller.
 */
public class TestKitFactory {

    // --- 1. Допоміжні методи для створення поодиноких товарів ---

    public static Yarn createYarnBordova() {
        return new Yarn(null, "Пряжа Alize Lanagold Бордова", "Класична напіввовняна пряжа",
                new BigDecimal("120.00"), BigDecimal.ZERO, MeasureUnit.SKEIN, 350, true, "/images/plugs/yarn.png",
                "ART-1201", "ЯрнОптТорг", "Alize", "Туреччина", "51% акрил, 49% вовна", 240, 100, "LOT-88210", "Бордовий", "Спиці 4-6 мм");
    }

    public static SewingThread createThreadBlack() {
        return new SewingThread(null, "ART-1001", "Нитки Gutermann Чорні",
                "Універсальні міцні нитки", new BigDecimal("75.00"), BigDecimal.ZERO, MeasureUnit.METER, 300, true,
                "/images/plugs/thread.png", "НиткиОпт", "Gutermann", "Австрія", "Універсальна",
                "100% поліестер", "№40", 200, "Чорний");
    }

    public static SewingThread createThreadWhite() {
        return new SewingThread(null, "ART-1002", "Нитки Gutermann Білі",
                "Універсальні міцні нитки", new BigDecimal("75.00"), BigDecimal.ZERO, MeasureUnit.METER, 300, true,
                "/images/plugs/thread.png", "НиткиОпт", "Gutermann", "Австрія", "Універсальна",
                "100% поліестер", "№40", 200, "Білий");
    }

    public static Tool createToolCircularSpokes() {
        return new Tool(null, "ART-1102", "Спиці кругові KnitPro 3.5мм", "Дерев'яні спиці на тросику",
                new BigDecimal("250.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 50, true, "/images/plugs/tool.png",
                "В'язальний Рай", "KnitPro", "Німеччина", "Спиці кругові", "Дерево", "3.5мм");
    }

    // --- 2. Базові методи для генерації Наборів (Kits) ---

    /**
     * Набір 'Старт в'язання' (3 мотки бордової пряжі + 1 кругові спиці). Без знижки за набір.
     */
    public static Kit createKitStartKnitting() {
        Kit kit = new Kit(null, "ART-1301", "Набір 'Старт в'язання'",
                "Все необхідне для першого шарфа", BigDecimal.ZERO, true, "/images/plugs/kit.png");

        kit.add(createYarnBordova(), 3);
        kit.add(createToolCircularSpokes(), 1);
        return kit;
    }

    /**
     * Набір 'Швачка-початківець' (2 котушки чорних + 2 білих ниток). Знижка 10%.
     */
    public static Kit createKitBeginnerSeamstress() {
        Kit kit = new Kit(null, "ART-1302", "Набір 'Швачка-початківець'",
                "Базові нитки для шиття", new BigDecimal("0.10"), true, "/images/plugs/kit.png");

        kit.add(createThreadBlack(), 2);
        kit.add(createThreadWhite(), 2);
        return kit;
    }

}