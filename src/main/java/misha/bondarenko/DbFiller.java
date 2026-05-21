package misha.bondarenko;

import misha.bondarenko.entities.products.*;
import misha.bondarenko.enums.MeasureUnit;
import misha.bondarenko.services.CatalogService;
import misha.bondarenko.services.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Configuration
public class DbFiller {

    @Bean
    @Transactional
    public CommandLineRunner fillDatabase(CatalogService catalogService, ProductService itemService) {
        boolean shouldWork = false;
        if (!shouldWork) return args -> {};

        return args -> {
            Catalog accessories = new Catalog(null, "Аксесуари", "Гудзики, бісер, прикраси для виробів тощо", "/images/buttons.jpg");
            Catalog books = new Catalog(null, "Книги з рукоділля", "Книги про в'язання іграшок, шиття одежі, прикрашання виробів тощо", "/images/book.jpg");
            Catalog equipment = new Catalog(null, "Обладнання", "Швейні машинки та ще щось", "/images/sewing_machine.jpg");
            Catalog fabrics = new Catalog(null, "Тканина", "Різна тканина для різних цілей", "/images/multi-color-fabric-texture-samples.jpg");
            Catalog fillers = new Catalog(null, "Наповнювачі", "Синтепух, вата, пір'я - все для наповнення", "/images/filler.jpg");
            Catalog giftCertificates = new Catalog(null, "Подарункові сертифікати", "Сертифікати, щоб радувати близьких та друзів", "/images/gift_certificate.jpg");
            Catalog patterns = new Catalog(null, "Схеми", "Схеми в'язання, шиття тощо", "/images/pattern.jpg");
            Catalog sewingThreads = new Catalog(null, "Нитки для шиття", "Різноманітні нитки для шиття та вишивання", "/images/sewing_threads.jpg");
            Catalog tools = new Catalog(null, "Інструменти", "Інструменти для шиття, в'язання; також допоміжні приладдя", "/images/knitting-tools-table.jpg");
            Catalog yarns = new Catalog(null, "Пряжа", "Пряжа для в'язання", "/images/image1.jpeg");
            Catalog kits = new Catalog(null, "Набори", "Готові набори для в'язання та шиття", "/images/kit.jpg");

            // --- 1. Аксесуари (Accessory) ---
            // Порядок: id, article, name, description, price, discount, unit, stockQuantity, isAvailable, imageUrl, supplier, brand, accessoryType, material, size, color
            accessories.add(
                    new Accessory(null, "ART-301", "Ґудзик пластиковий 15мм", "Декоративний ґудзик для кардиганів",
                            new BigDecimal("15.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 500, true, "/images/btn1.jpg",
                            "Бабуся Надія ТМ", "Prym", "Ґудзик", "Пластик", "15мм", "Чорний"),
                    new Accessory(null, "ART-302", "Набір маркерів для петель", "Кольорові пластикові маркери",
                            new BigDecimal("85.00"), new BigDecimal("0.05"), MeasureUnit.UNIT, 200, true, "/images/markers.jpg",
                            "В'язальний Рай", "KnitPro", "Маркери петель", "Пластик", "Універсальний", "Мікс"),
                    new Accessory(null, "ART-303", "Набір ручних голок", "Міцні голки для зшивання деталей",
                            new BigDecimal("45.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 300, true, "/images/needles.jpg",
                            "ШвачкаОпт", "Prym", "Голки швейні", "Сталь", "Асорті", "Сріблястий")
            );

            // --- 2. Книги (Book) ---
            // Порядок: id, name, description, price, discount, unit, stockQuantity, isAvailable, imageUrl, article, supplier, brand, author, publisher, isbn, pages, publicationYear
            books.add(
                    new Book(null, "Велика енциклопедія в'язання", "Детальний посібник з безліччю схем",
                            new BigDecimal("450.00"), new BigDecimal("0.10"), MeasureUnit.UNIT, 20, true, "/images/book1.jpg",
                            "ART-401", "Книготорг", "КСД", "Елізабет Кент", "КСД", "978-617-12", 280, 2024),
                    new Book(null, "В'яжемо іграшки амігурумі", "Схеми милих іграшок",
                            new BigDecimal("380.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 15, true, "/images/book2.jpg",
                            "ART-402", "Книготорг", "Віват", "Марія Іванова", "Віват", "978-966-982", 200, 2023),
                    new Book(null, "Шиття для початківців Knit&Sew", "Основи створення одягу",
                            new BigDecimal("520.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 10, true, "/images/book3.jpg",
                            "ART-403", "Книготорг", "Наш Формат", "Джейн Сміт", "Наш Формат", "978-617-78", 320, 2022)
            );

            // --- 3. Обладнання (Equipment) ---
            // Порядок: id, name, description, price, discount, unit, stockQuantity, isAvailable, imageUrl, article, supplier, brand, equipmentType, warrantyMonths, powerWatt, dimensions, weightKg, operationsCount
            equipment.add(
                    new Equipment(null, "Швейна машина Singer 23", "Надійна електромеханічна машина",
                            new BigDecimal("5500.00"), new BigDecimal("0.05"), MeasureUnit.UNIT, 5, true, "/images/eq1.jpg",
                            "ART-501", "ТехноШвачка", "Singer", "Швейна машина", 24, 70, "40x30x20 см", 6.5, 23),
                    new Equipment(null, "Оверлок Janome 4-нитковий", "Професійний оверлок для країв",
                            new BigDecimal("7200.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 3, true, "/images/eq2.jpg",
                            "ART-502", "ТехноШвачка", "Janome", "Оверлок", 12, 90, "35x35x30 см", 7.0, 4),
                    new Equipment(null, "Швейно-вишивальна машина Brother", "Багатофункціональний пристрій",
                            new BigDecimal("9800.00"), new BigDecimal("0.10"), MeasureUnit.UNIT, 2, true, "/images/eq3.jpg",
                            "ART-503", "ТехноШвачка", "Brother", "Швейно-вишивальна", 36, 120, "50x40x30 см", 10.0, 80)
            );

            // --- 4. Тканини (Fabric) ---
            // Порядок: id, name, description, price, discount, unit, stockQuantity, isAvailable, imageUrl, article, supplier, brand, fabricType, composition, widthInCm, density, color
            fabrics.add(
                    new Fabric(null, "Бавовна біла натуральна", "Ідеальна для пошиття літніх речей",
                            new BigDecimal("180.00"), BigDecimal.ZERO, MeasureUnit.METER, 100, true, "/images/fab1.jpg",
                            "ART-601", "ТекстильОпт", "БавовнаТрейд", "Бавовна", "100% бавовна", 150, 130, "Білий"),
                    new Fabric(null, "Льон пом'якшений сірий", "Екологічний матеріал для суконь та сорочок",
                            new BigDecimal("290.00"), new BigDecimal("0.05"), MeasureUnit.METER, 50, true, "/images/fab2.jpg",
                            "ART-602", "ТекстильОпт", "ЛьонПлюс", "Льон", "100% льон", 140, 180, "Сірий"),
                    new Fabric(null, "Шовк натуральний червоний", "Розкішний шовк преміум-класу",
                            new BigDecimal("850.00"), BigDecimal.ZERO, MeasureUnit.METER, 30, true, "/images/fab3.jpg",
                            "ART-603", "ТекстильОпт", "ШовкСтайл", "Шовк", "100% шовк", 110, 80, "Червоний")
            );

            // --- 5. Наповнювачі (Filler) ---
            // Порядок: id, name, description, price, discount, unit, stockQuantity, isAvailable, imageUrl, article, supplier, brand, fillerType, density, isHypoallergenic, packageWeightKg
            fillers.add(
                    new Filler(null, "Холофайбер гіпоалергенний 1 кг", "М'який наповнювач для іграшок та подушок",
                            new BigDecimal("150.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 40, true, "/images/fil1.jpg",
                            "ART-701", "ЕкоСинтетика", "Холофайбер", "Холофайбер", 100, true, 1.0),
                    new Filler(null, "Синтепух м'який 0.5 кг", "Легкий об'ємний наповнювач",
                            new BigDecimal("85.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 60, true, "/images/fil2.jpg",
                            "ART-702", "ЕкоСинтетика", "Синтепух", "Синтепух", 150, false, 0.5),
                    new Filler(null, "Флізелін клейовий відривний", "Матеріал для ущільнення тканин",
                            new BigDecimal("45.00"), new BigDecimal("0.10"), MeasureUnit.METER, 100, true, "/images/fil3.jpg",
                            "ART-703", "УкрДублерин", "Флізелін", "Флізелін клейовий", 40, false, 0.1)
            );

            // --- 6. Подарункові сертифікати (GiftCertificate) ---
            // Порядок: id, name, description, price, discount, unit, stockQuantity, isAvailable, imageUrl, article, supplier, brand, certificateType, validityMonths, termsOfUse
            giftCertificates.add(
                    new GiftCertificate(null, "Сертифікат номіналом 500 грн", "Ідеальний подарунок для творчої людини",
                            new BigDecimal("500.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 999, true, "/images/cert1.jpg",
                            "ART-801", "Knit&Sew Офіс", "Knit&Sew", "Електронний", 12, "Діє на всі товари"),
                    new GiftCertificate(null, "Сертифікат номіналом 1000 грн", "Чудовий подарунок до свята",
                            new BigDecimal("1000.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 999, true, "/images/cert2.jpg",
                            "ART-802", "Knit&Sew Офіс", "Knit&Sew", "Електронний", 12, "Діє на всі товари"),
                    new GiftCertificate(null, "Пластиковий сертифікат 2000 грн", "Фізична картка у гарному пакуванні",
                            new BigDecimal("2000.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 999, true, "/images/cert3.jpg",
                            "ART-803", "Knit&Sew Офіс", "Knit&Sew", "Пластиковий", 24, "Діє на всі товари")
            );

            // --- 7. Схеми (Pattern) ---
            // Порядок: id, name, description, price, discount, unit, stockQuantity, isAvailable, imageUrl, article, supplier, brand, author, difficultyLevel, language, format
            patterns.add(
                    new Pattern(null, "Схема светра 'Оверсайз'", "Детальний опис з відео-підказками",
                            new BigDecimal("150.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 999, true, "/images/pat1.jpg",
                            "ART-901", "KnitDesign", "KnitDesign", "Анна Петрова", "Середній", "Українська", "PDF"),
                    new Pattern(null, "Схема іграшки 'Ведмедик'", "Схема для в'язання гачком (амігурумі)",
                            new BigDecimal("90.00"), new BigDecimal("0.10"), MeasureUnit.UNIT, 999, true, "/images/pat2.jpg",
                            "ART-902", "ToyMaker", "ToyMaker", "Марія Іванова", "Початковий", "Українська", "PDF"),
                    new Pattern(null, "Викрійка літньої сукні Burda", "Офіційна паперова викрійка",
                            new BigDecimal("250.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 50, true, "/images/pat3.jpg",
                            "ART-903", "SewingPatterns", "SewingPatterns", "Burda", "Складний", "Англійська", "Друкований")
            );

            // --- 8. Нитки для шиття (SewingThread) ---
            // Порядок: id, name, description, price, discount, unit, stockQuantity, isAvailable, imageUrl, article, supplier, brand, threadType, composition, thickness, lengthInMeters, color
            SewingThread thr1 = new SewingThread(null, "Нитки Gutermann універсальні чорні", "Універсальні міцні нитки",
                    new BigDecimal("75.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 300, true, "/images/thr1.jpg",
                    "ART-1001", "НиткиОпт", "Gutermann", "Універсальна", "100% поліестер", "№40", 200, "Чорний");
            SewingThread thr2 = new SewingThread(null, "Нитки Gutermann універсальні білі", "Універсальні міцні нитки",
                    new BigDecimal("75.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 300, true, "/images/thr2.jpg",
                    "ART-1002", "НиткиОпт", "Gutermann", "Універсальна", "100% поліестер", "№40", 200, "Білий");
            SewingThread thr3 = new SewingThread(null, "Нитки вишивальні Madeira золоті", "Металізована нитка для вишивки",
                    new BigDecimal("120.00"), new BigDecimal("0.05"), MeasureUnit.UNIT, 150, true, "/images/thr3.jpg",
                    "ART-1003", "НиткиОпт", "Madeira", "Вишивальна", "Віскоза/Металік", "№40", 1000, "Золотий");
            sewingThreads.add(thr1, thr2, thr3);

            // --- 9. Інструменти (Tool) ---
            // Порядок: id, name, description, price, discount, unit, stockQuantity, isAvailable, imageUrl, article, supplier, brand, toolType, material, size
            Tool tool1 = new Tool(null, "Гачок алюмінієвий 4мм", "Зручний гачок для пряжі",
                    new BigDecimal("56.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 190, true, "/images/tool1.jpg",
                    "ART-1101", "Бабуся Надія ТМ", "ДебільніГачки", "Гачок", "Алюміній", "4мм");
            Tool tool2 = new Tool(null, "Спиці кругові KnitPro 3.5мм", "Дерев'яні спиці на тросику",
                    new BigDecimal("250.00"), new BigDecimal("0.10"), MeasureUnit.UNIT, 50, true, "/images/tool2.jpg",
                    "ART-1102", "В'язальний Рай", "KnitPro", "Спиці кругові", "Дерево", "3.5мм");
            Tool tool3 = new Tool(null, "Ножиці для вишивання Prym", "Гострі кравецькі ножиці",
                    new BigDecimal("80.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 120, true, "/images/tool3.jpg",
                    "ART-1103", "ШвачкаОпт", "Prym", "Ножиці", "Сталь", "13см");
            tools.add(tool1, tool2, tool3);

            // --- 10. Пряжа (Yarn) ---
            // Порядок: id, name, description, price, discount, unit, stockQuantity, isAvailable, imageUrl, article, supplier, brand, fiberContent, lengthInMeters, weightInGrams, dyeLot, color, toolsRecommended
            Yarn yarn1 = new Yarn(null, "Пряжа Alize Lanagold Бордова", "Класична напіввовняна пряжа",
                    new BigDecimal("120.00"), new BigDecimal("0.08"), MeasureUnit.UNIT, 350, true, "/images/yarn1.jpg",
                    "ART-1201", "ЯрнОптТорг", "Alize", "51% акрил, 49% вовна", 240, 100, "LOT-88210", "Бордовий", "Спиці 4-6 мм");
            Yarn yarn2 = new Yarn(null, "Пряжа Alize Lanagold Синя", "Класична напіввовняна пряжа",
                    new BigDecimal("120.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 350, true, "/images/yarn2.jpg",
                    "ART-1202", "ЯрнОптТорг", "Alize", "51% акрил, 49% вовна", 240, 100, "LOT-88210", "Синій", "Спиці 4-6 мм");
            Yarn yarn3 = new Yarn(null, "Пряжа YarnArt Jeans Біла", "Бавовняна пряжа для літніх речей",
                    new BigDecimal("150.00"), new BigDecimal("0.10"), MeasureUnit.UNIT, 200, true, "/images/yarn3.jpg",
                    "ART-1203", "ПряжаТрейд", "YarnArt", "55% бавовна, 45% поліакрил", 160, 50, "LOT-123", "Білий", "Гачок 2-3.5 мм");
            yarns.add(yarn1, yarn2, yarn3);

            // --- 11. Набори (Kit) ---
            // Порядок для Kit: id, article, name, description, price (базова для конструктора Item, ставимо ZERO), discount, unit, stockQuantity, isAvailable, imageUrl
            Kit kit1 = new Kit(null, "ART-1301", "Набір 'Старт в'язання'", "Все необхідне для першого шарфа",
                    BigDecimal.ZERO, BigDecimal.ZERO, MeasureUnit.UNIT, 20, true, "/images/kit1.jpg");
            kit1.setKitDiscount(new BigDecimal("0.10")); // 10% знижка на набір
            kit1.add(yarn1, 3); // 3 мотки пряжі
            kit1.add(tool2, 1); // 1 пара спиць

            Kit kit2 = new Kit(null, "ART-1302", "Набір 'Швачка-початківець'", "Базові нитки для шиття",
                    BigDecimal.ZERO, BigDecimal.ZERO, MeasureUnit.UNIT, 15, true, "/images/kit2.jpg");
            kit2.add(thr1, 2); // 2 котушки чорних ниток
            kit2.add(thr2, 2); // 2 котушки білих ниток

            Kit kit3 = new Kit(null, "ART-1303", "Набір 'Велика іграшка'", "Пряжа та наповнювач для амігурумі",
                    BigDecimal.ZERO, BigDecimal.ZERO, MeasureUnit.UNIT, 10, true, "/images/kit3.jpg");
            kit3.setKitDiscount(new BigDecimal("0.15")); // 15% знижка
            kit3.add(yarn3, 5); // 5 мотків пряжі
            kit3.add(tool1, 1); // гачок

            kits.add(kit1, kit2, kit3);

            catalogService.saveAll(
                    accessories, books, equipment, fabrics, fillers,
                    giftCertificates, patterns, sewingThreads, tools, yarns, kits
            );

        };
    }
}
