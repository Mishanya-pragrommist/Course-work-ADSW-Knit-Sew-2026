package misha.bondarenko.entities.products;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {
    private Accessory accessory;
    private Book book;
    private Equipment equipment;
    private Fabric fabric;
    private Filler filler;
    private GiftCertificate giftCertificate;
    private Kit kit;
    private Pattern pattern;
    private SewingThread sewingThread;
    private Tool tool;
    private Yarn yarn;

    @BeforeEach
    public void setUp() {
        Random random = new Random();

        // 1. Ініціалізація одиничних неподільних товарів (нащадків Product)

        // Фурнітура: голки, маркери, ґудзики
        accessory = new Accessory();
        accessory.setId(random.nextLong(100));
        accessory.setArticle("ACC-7701");
        accessory.setName("Маркери для в'язання пластикові");
        accessory.setDescription("Набір різнокольорових маркерів для петель замкненого типу, 30 шт.");
        accessory.setBasePrice(new BigDecimal("85.00"));
        accessory.setStockQuantity(15);
        accessory.setAvailable(true);
        accessory.setBrand("Clover");
        accessory.setSupplier("ТОВ ПромТекс");
        accessory.setCountry("Японія");
        accessory.setAccessoryType("Маркери петель");
        accessory.setMaterial("Пластик");
        accessory.setSize("Універсальний");
        accessory.setColor("Мікс");

        // Книги та журнали з рукоділля
        book = new Book();
        book.setId(2L);
        book.setArticle("BOK-1092");
        book.setName("Енциклопедія сучасних візерунків");
        book.setDescription("Покрокове керівництво з в'язання спицями та гачком з детальними кольоровими схемами.");
        book.setBasePrice(new BigDecimal("450.00"));
        book.setStockQuantity(4);
        book.setAvailable(true);
        book.setBrand("Клуб Сімейного Дозвілля");
        book.setSupplier("Видавництво КСД");
        book.setCountry("Україна");
        book.setAuthor("Ганна Радченко");
        book.setPublisher("КСД");
        book.setIsbn("978-617-12-9950-2");
        book.setPages(256);
        book.setPublicationYear(2024);

        equipment = new Equipment();
        equipment.setId(random.nextLong(100));
        equipment.setName("Швейна машинка Singer");
        equipment.setEquipmentType("Швейна машинка");
        equipment.setDimensions("30*40*50 см");
        equipment.setBasePrice(new BigDecimal("450.00"));
        equipment.setStockQuantity(15);
        equipment.setAvailable(true);
        equipment.setBrand("Clover");
        equipment.setSupplier("random supplier");
        equipment.setArticle("EQT-9012");
        equipment.setPowerWatt(600);
        equipment.setWarrantyMonths(18);

        // Тканини
        fabric = new Fabric();
        fabric.setId(random.nextLong(100));
        fabric.setArticle("FAB-2204");
        fabric.setName("Льон натуральний пом'якшений");
        fabric.setDescription("Преміальна лляна тканина для пошиття літнього одягу та домашнього текстилю.");
        fabric.setBasePrice(new BigDecimal("320.00")); // ціна за метр
        fabric.setStockQuantity(50);
        fabric.setAvailable(true);
        fabric.setBrand("BelLinen");
        fabric.setSupplier("ІмпортТекстиль");
        fabric.setCountry("Білорусь");
        fabric.setFabricType("Льон");
        fabric.setComposition("100% льон");
        fabric.setWidthInCm(150); // см
        fabric.setDensity(180); // г/м²
        fabric.setColor("Натуральний сірий");

        // Наповнювачі та ущільнювачі
        filler = new Filler();
        filler.setId(random.nextLong(100));
        filler.setArticle("FIL-3401");
        filler.setName("Холлофайбер первинний (кульки)");
        filler.setDescription("Гіпоалергенний наповнювач для м'яких іграшок, подушок та бортиків.");
        filler.setFillerType("Холлофайбер");
        filler.setBasePrice(new BigDecimal("140.00")); // за пакування
        filler.setStockQuantity(20);
        filler.setAvailable(true);
        filler.setBrand("УкрНаповнювач");
        filler.setSupplier("Фабрика Комфорту");
        filler.setCountry("Україна");
        filler.setDensity(0); // Для пуху щільність не є ключовою, або вказується 0
        filler.setHypoallergenic(true);
        filler.setPackageWeightKg(1); // 1 кг

        // Подарункові сертифікати
        giftCertificate = new GiftCertificate();
        giftCertificate.setId(random.nextLong(100));
        giftCertificate.setArticle("CERT-500");
        giftCertificate.setName("Подарунковий сертифікат Knit&Sew");
        giftCertificate.setDescription("Електронний сертифікат на будь-які покупки в нашому онлайн-магазині.");
        giftCertificate.setBasePrice(new BigDecimal("500.00"));
        giftCertificate.setStockQuantity(999); // Безлімітний цифровий товар
        giftCertificate.setAvailable(true);
        giftCertificate.setBrand("Knit&Sew");
        giftCertificate.setSupplier("Власний бренд");
        giftCertificate.setCountry("Україна");
        giftCertificate.setCertificateType("Електронний, PDF");
        giftCertificate.setValidityMonths(12); // 1year

        // Авторські інструкції та схеми для в'язання (електронні або друк)
        pattern = new Pattern();
        pattern.setId(random.nextLong(100));
        pattern.setArticle("PAT-0045");
        pattern.setName("Схема в'язання кардигана 'Oversize'");
        pattern.setDescription("Детальний PDF-опис з відео-підказками для в'язання базового кардигана.");
        pattern.setBasePrice(new BigDecimal("120.00"));
        pattern.setStockQuantity(999);
        pattern.setAvailable(true);
        pattern.setBrand("Knitting_Design");
        pattern.setSupplier("ФОП Бондаренко");
        pattern.setCountry("Україна");
        pattern.setAuthor("Марія Прохорова");
        pattern.setDifficultyLevel("Середній");
        pattern.setLanguage("Українська");
        pattern.setFormat("Цифровий (PDF)");

        // Швейні нитки
        sewingThread = new SewingThread();
        sewingThread.setId(random.nextLong(100));
        sewingThread.setArticle("THR-4002");
        sewingThread.setName("Нитка швейна Gutermann 100м");
        sewingThread.setDescription("Універсальна високоякісна поліестерова нитка для будь-яких тканин.");
        sewingThread.setBasePrice(new BigDecimal("65.00"));
        sewingThread.setStockQuantity(45);
        sewingThread.setAvailable(true);
        sewingThread.setBrand("Gutermann");
        sewingThread.setSupplier("Текс-Дизайн");
        sewingThread.setCountry("Німеччина");
        sewingThread.setThreadType("Універсальна");
        sewingThread.setComposition("100% поліестер");
        sewingThread.setThickness("№100");
        sewingThread.setLengthInMeters(100);
        sewingThread.setColor("Чорний (код 000)");

        // Інструменти (спиці, гачки)
        tool = new Tool();
        tool.setId(random.nextLong(100));
        tool.setArticle("TOL-8840");
        tool.setName("Спиці кругові ChiaoGoo Red Lace");
        tool.setDescription("Професійні металеві кругові спиці з червоною лескою без пам'яті.");
        tool.setBasePrice(new BigDecimal("520.00"));
        tool.setStockQuantity(8);
        tool.setAvailable(true);
        tool.setBrand("ChiaoGoo");
        tool.setSupplier("Ланцюг Майстринь");
        tool.setCountry("США/Китай");
        tool.setToolType("Кругові спиці");
        tool.setMaterial("Нержавіюча сталь");
        tool.setSize("3.5 мм / 80 см");

        // Пряжа
        yarn = new Yarn();
        yarn.setId(random.nextLong(100));
        yarn.setArticle("YRN-5512");
        yarn.setName("Пряжа Alize LanaGold Classic");
        yarn.setDescription("Класична напіввовняна пряжа для затишних зимових речей.");
        yarn.setBasePrice(new BigDecimal("115.00"));
        yarn.setStockQuantity(30);
        yarn.setAvailable(true);
        yarn.setBrand("Alize");
        yarn.setSupplier("ФОП ТекстильОпт");
        yarn.setCountry("Туреччина");
        yarn.setFiberContent("49% вовна, 51% акрил");
        yarn.setWeightInGrams(100);
        yarn.setLengthInMeters(240);
        yarn.setDyeLot("LOT-77412");
        yarn.setColor("Бордовий (код 57)");

        // 2. Ініціалізація Композита (Набір для рукоділля - Kit)
        // Створюємо набір "Зимовий затишок" зі знижкою 10% (0.10) на весь сет
        kit = new Kit();
        kit.setId(random.nextLong(100));
        kit.setName("Набір для в'язання 'Зимовий Шарф'");
        kit.setArticle("KIT-100");
        kit.setDescription("Повний комплект матеріалів та інструкцій для створення стильного теплого шарфа.");
        kit.setDiscount(new BigDecimal("0.10"));

        // Наповнюємо набір компонентами через твій метод add(Item item, int quantity)
        // До складу входять: 3 мотки пряжі Yarn, 1 спиці Tool та 1 схема Pattern
        kit.add(yarn, 3);
        kit.add(tool, 1);
        kit.add(pattern, 1);
    }

    @Test
    @DisplayName("Утворення назв елементів для відображення на вебсторінці")
    void renderNameTest() {
        List<Item> items = List.of(
                accessory,
                book,
                equipment,
                fabric,
                filler,
                giftCertificate,
                kit,
                pattern,
                sewingThread,
                tool,
                yarn
        );
        System.out.println("Назви товарів, готові для вставки на вебсторінку:\n");
        items.forEach(item -> System.out.println(item.renderName()));

        String[] names = new String[] {
                "Маркери для в'язання пластикові Clover, мікс, пластик, діаметр Універсальний, ACC-7701",
                "Книга \"Енциклопедія сучасних візерунків\" - Ганна Радченко, видавництво КСД, 2024 р., BOK-1092",
                "Швейна машинка Clover, 600W, гарантія: 18 міс., EQT-9012",
                "Тканина Льон натуральний пом'якшений BelLinen, натуральний сірий, 100% льон, ширина рул. 150 см, FAB-2204",
                "Холлофайбер первинний (кульки), УкрНаповнювач, 0 г/м², пакування 1.0 кг, FIL-3401",
                "Подарунковий сертифікат, Електронний, PDF, номіналом 500.00 грн, CERT-500",
                "Набір для в'язання 'Зимовий Шарф' (3 комп.), KIT-100",
                "Схема в'язання кардигана 'Oversize' від автора Марія Прохорова, середній рівень, Цифровий (PDF), PAT-0045",
                "Нитка Універсальна Gutermann, чорний (код 000), товщина №100, довжина 100м, THR-4002",
                "Кругові спиці ChiaoGoo, нержавіюча сталь, розмір 3.5 мм / 80 см, TOL-8840",
                "Пряжа Alize, бордовий (код 57), 49% вовна, 51% акрил, 100г, LOT-77412, YRN-5512"
        };
        for (int i = 0; i < names.length; i++) {
            assertEquals(names[i].toLowerCase(), items.get(i).renderName().toLowerCase());
        }
    }

    @Test
    @DisplayName("Тестування роботи з ціною товару")
    void testPriceCount() {
        // It is enough to use only one object since methods to get basePrice and discount
        // is implemented in super class Product

        BigDecimal basePrice = new BigDecimal("85.00");
        BigDecimal discount = new BigDecimal("0.01");

        assertEquals(basePrice, accessory.getBasePrice());

        // 1) When discount is not set and getTotalPrice() is called which uses discount
        // it should return the same result as getBasePrice()
        assertEquals(basePrice, accessory.getTotalPrice());

        accessory.setDiscount(new BigDecimal("0.01"));

        // 2) When discount IS set, getTotalPrice should consider it
        assertEquals(basePrice.subtract(basePrice.multiply(discount)), accessory.getTotalPrice());

    }

}
