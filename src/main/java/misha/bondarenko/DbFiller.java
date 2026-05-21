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
        boolean shouldWork = true;
        if (!shouldWork) return null;

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

            // --- Пряжа ---
            Yarn yarn1 = new Yarn(true, "YAR-01", "ART-101", "ЯрнОптТорг", "Alize", 350, MeasureUnit.UNIT, new BigDecimal("120.00"), new BigDecimal("0.08"), "51% акрил, 49% вовна", 240, 100, "LOT-88210", "Бордовий");
            yarn1.setName("Пряжа Alize Lanagold Бордова");
            Yarn yarn2 = new Yarn(true, "YAR-02", "ART-102", "ЯрнОптТорг", "Alize", 350, MeasureUnit.UNIT, new BigDecimal("120.00"), BigDecimal.ZERO, "51% акрил, 49% вовна", 240, 100, "LOT-88210", "Синій");
            yarn2.setName("Пряжа Alize Lanagold Синя");
            Yarn yarn3 = new Yarn(true, "YAR-03", "ART-103", "ПряжаТрейд", "YarnArt", 200, MeasureUnit.UNIT, new BigDecimal("150.00"), new BigDecimal("0.10"), "100% бавовна", 160, 50, "LOT-123", "Білий");
            yarn3.setName("Пряжа YarnArt Jeans Біла");
            yarns.add(yarn1); yarns.add(yarn2); yarns.add(yarn3);

            // --- Інструменти ---
            Tool tool1 = new Tool(true, "TOL-01", "ART-201", "Бабуся Надія ТМ", "ДебільніГачки", 190, MeasureUnit.UNIT, new BigDecimal("56.00"), BigDecimal.ZERO, "Гачок", "Алюміній", "4мм");
            tool1.setName("Гачок алюмінієвий 4мм");
            Tool tool2 = new Tool(true, "TOL-02", "ART-202", "В'язальний Рай", "KnitPro", 50, MeasureUnit.UNIT, new BigDecimal("250.00"), new BigDecimal("0.10"), "Спиці кругові", "Дерево", "3.5мм");
            tool2.setName("Спиці кругові KnitPro 3.5мм");
            Tool tool3 = new Tool(true, "TOL-03", "ART-203", "ШвачкаОпт", "Prym", 120, MeasureUnit.UNIT, new BigDecimal("80.00"), BigDecimal.ZERO, "Ножиці", "Сталь", "13см");
            tool3.setName("Ножиці для вишивання Prym");
            tools.add(tool1); tools.add(tool2); tools.add(tool3);

            // --- Аксесуари ---
            Accessory acc1 = new Accessory(true, "ACC-01", "ART-301", "ФурнітураОпт", "Prym", 500, MeasureUnit.UNIT, new BigDecimal("15.00"), BigDecimal.ZERO, "Ґудзик", "Пластик", "15мм", "Чорний");
            acc1.setName("Ґудзик пластиковий 15мм");
            Accessory acc2 = new Accessory(true, "ACC-02", "ART-302", "В'язальний Рай", "KnitPro", 200, MeasureUnit.UNIT, new BigDecimal("85.00"), new BigDecimal("0.05"), "Маркери петель", "Пластик", "Універсальний", "Мікс");
            acc2.setName("Набір маркерів для петель KnitPro");
            Accessory acc3 = new Accessory(true, "ACC-03", "ART-303", "ШвачкаОпт", "Prym", 300, MeasureUnit.UNIT, new BigDecimal("45.00"), BigDecimal.ZERO, "Голки швейні", "Сталь", "Асорті", "Сріблястий");
            acc3.setName("Набір ручних голок Prym");
            accessories.add(acc1); accessories.add(acc2); accessories.add(acc3);

            // --- Книги ---
            Book book1 = new Book(true, "BOK-01", "ART-401",
                    "Книготорг", "КСД", 20, MeasureUnit.UNIT,
                    new BigDecimal("450.00"), new BigDecimal("0.10"),
                    "Велика енциклопедія в'язання", "Елізабет Кент", "КСД", "978-617-12", 280, 2024);

            Book book2 = new Book(true, "BOK-02", "ART-402",
                    "Книготорг", "Віват", 15, MeasureUnit.UNIT,
                    new BigDecimal("380.00"), BigDecimal.ZERO,
                    "В'яжемо іграшки амігурумі","Марія Іванова", "Віват", "978-966-982", 200, 2023);
            Book book3 = new Book(true, "BOK-03", "ART-403",
                    "Книготорг", "Наш Формат", 10, MeasureUnit.UNIT,
                    new BigDecimal("520.00"), BigDecimal.ZERO,
                    "Шиття для початківців","Джейн Сміт", "Наш Формат", "978-617-78", 320, 2022);
            books.add(book1, book2, book3);

            // --- Обладнання ---
            Equipment eq1 = new Equipment(true, "EQP-01", "ART-501", "ТехноШвачка", "Singer", 5, MeasureUnit.UNIT, new BigDecimal("5500.00"), new BigDecimal("0.05"), "Швейна машина", 24, 70, "40x30x20", 6.5, 23);
            eq1.setName("Швейна машина Singer 23");
            Equipment eq2 = new Equipment(true, "EQP-02", "ART-502", "ТехноШвачка", "Janome", 3, MeasureUnit.UNIT, new BigDecimal("7200.00"), BigDecimal.ZERO, "Оверлок", 12, 90, "35x35x30", 7.0, 4);
            eq2.setName("Оверлок Janome 4-нитковий");
            Equipment eq3 = new Equipment(true, "EQP-03", "ART-503", "ТехноШвачка", "Brother", 2, MeasureUnit.UNIT, new BigDecimal("9800.00"), new BigDecimal("0.10"), "Швейно-вишивальна", 36, 120, "50x40x30", 10.0, 80);
            eq3.setName("Швейно-вишивальна машина Brother");
            equipment.add(eq1); equipment.add(eq2); equipment.add(eq3);

            // --- Тканини ---
            Fabric fab1 = new Fabric(false, "FAB-01", "ART-601", "ТекстильОпт", "БавовнаТрейд", 100, MeasureUnit.METERS, new BigDecimal("180.00"), BigDecimal.ZERO, "Бавовна", "100% бавовна", 150, 130, "Білий");
            fab1.setName("Бавовна біла натуральна");
            Fabric fab2 = new Fabric(true, "FAB-02", "ART-602", "ТекстильОпт", "ЛьонПлюс", 50, MeasureUnit.METERS, new BigDecimal("290.00"), new BigDecimal("0.05"), "Льон", "100% льон", 140, 180, "Сірий");
            fab2.setName("Льон пом'якшений сірий");
            Fabric fab3 = new Fabric(true, "FAB-03", "ART-603", "ТекстильОпт", "ШовкСтайл", 30, MeasureUnit.METERS, new BigDecimal("850.00"), BigDecimal.ZERO, "Шовк", "100% шовк", 110, 80, "Червоний");
            fab3.setName("Шовк натуральний червоний");
            fabrics.add(fab1); fabrics.add(fab2); fabrics.add(fab3);

            // --- Наповнювачі ---
            Filler fil1 = new Filler(true, "FIL-01", "ART-701", "ЕкоСинтетика", "Холофайбер",
                    40, MeasureUnit.UNIT, new BigDecimal("150.00"), BigDecimal.ZERO, "Холофайбер", 100, true, 1.0);
            fil1.setName("Холофайбер гіпоалергенний 1 кг");
            Filler fil2 = new Filler(false, "FIL-02", "ART-702", "ЕкоСинтетика", "Синтепух",
                    60, MeasureUnit.UNIT, new BigDecimal("85.00"), BigDecimal.ZERO, "Синтепух", 150, false, 0.5);
            fil2.setName("Синтепух м'який 0.5 кг");
            Filler fil3 = new Filler(true, "FIL-03", "ART-703", "УкрДублерин", "Флізелін",
                    100, MeasureUnit.METERS, new BigDecimal("45.00"), new BigDecimal("0.10"), "Флізелін клейовий", 40, false, 0.1);
            fil3.setName("Флізелін клейовий відривний");
            fillers.add(fil1); fillers.add(fil2); fillers.add(fil3);

            // --- Подарункові сертифікати ---
            GiftCertificate cert1 = new GiftCertificate(true, "CRT-01", "ART-801", "Knit&Sew",
                    "Knit&Sew", 999, MeasureUnit.UNIT, new BigDecimal("500.00"), BigDecimal.ZERO, "Електронний", 12, "Діє на всі товари");
            cert1.setName("Сертифікат номіналом 500 грн");
            GiftCertificate cert2 = new GiftCertificate(true, "CRT-02", "ART-802", "Knit&Sew",
                    "Knit&Sew", 999, MeasureUnit.UNIT, new BigDecimal("1000.00"), BigDecimal.ZERO, "Електронний", 12, "Діє на всі товари");
            cert2.setName("Сертифікат номіналом 1000 грн");
            GiftCertificate cert3 = new GiftCertificate(false, "CRT-03", "ART-803", "Knit&Sew",
                    "Knit&Sew", 999, MeasureUnit.UNIT, new BigDecimal("2000.00"), BigDecimal.ZERO, "Пластиковий", 24, "Діє на всі товари");
            cert3.setName("Пластиковий сертифікат 2000 грн");
            giftCertificates.add(cert1); giftCertificates.add(cert2); giftCertificates.add(cert3);

            // --- Схеми ---
            Pattern pat1 = new Pattern(true, "PAT-01", "ART-901", "KnitDesign",
                    "KnitDesign", 999, MeasureUnit.UNIT,
                    new BigDecimal("150.00"), BigDecimal.ZERO, "Анна Петрова",
                    "Середній", "Українська", "PDF");
            pat1.setName("Схема светра 'Оверсайз'");
            Pattern pat2 = new Pattern(false, "PAT-02", "ART-902", "ToyMaker",
                    "ToyMaker", 999, MeasureUnit.UNIT,
                    new BigDecimal("90.00"), new BigDecimal("0.10"), "Марія Іванова",
                    "Початковий", "Українська", "PDF");
            pat2.setName("Схема іграшки 'Ведмедик'");
            Pattern pat3 = new Pattern(true, "PAT-03", "ART-903",
                    "SewingPatterns", "SewingPatterns", 50, MeasureUnit.UNIT,
                    new BigDecimal("250.00"), BigDecimal.ZERO, "Burda", "Складний",
                    "Англійська", "Друкований");
            pat3.setName("Викрійка літньої сукні Burda");
            patterns.add(pat1); patterns.add(pat2); patterns.add(pat3);

            // --- Нитки для шиття ---
            SewingThread thr1 = new SewingThread(true, "THR-01", "ART-1001",
                    "НиткиОпт", "Gutermann", 300, MeasureUnit.UNIT,
                    new BigDecimal("75.00"), BigDecimal.ZERO, "Універсальна",
                    "100% поліестер", "№40", 200, "Чорний");
            thr1.setName("Нитки Gutermann універсальні чорні");
            SewingThread thr2 = new SewingThread(true, "THR-02", "ART-1002",
                    "НиткиОпт", "Gutermann", 300, MeasureUnit.UNIT,
                    new BigDecimal("75.00"), BigDecimal.ZERO, "Універсальна",
                    "100% поліестер", "№40", 200, "Білий");
            thr2.setName("Нитки Gutermann універсальні білі");
            SewingThread thr3 = new SewingThread(false, "THR-03", "ART-1003",
                    "НиткиОпт", "Madeira", 150, MeasureUnit.UNIT,
                    new BigDecimal("120.00"), new BigDecimal("0.05"),
                    "Вишивальна", "Віскоза", "№40", 1000, "Золотий");
            thr3.setName("Нитки вишивальні Madeira золоті");
            sewingThreads.add(thr1); sewingThreads.add(thr2); sewingThreads.add(thr3);

            // --- Набори ---
            Kit kit1 = new Kit(null, "Набір 'Старт в'язання'", "Все необхідне для першого шарфа",
                    "image", new BigDecimal("0.10"));
            kit1.add(yarn1, tool2);

            Kit kit2 = new Kit(null, "Набір 'Швачка-початківець'", "Базові нитки та голки", "image", BigDecimal.ZERO);
            kit2.add(thr1, thr2, acc3);

            Kit kit3 = new Kit(null, "Набір 'Велика іграшка'", "Пряжа та наповнювач",
                    "image" ,new BigDecimal("0.15"));
            kit3.add(yarn3, fil1, pat2);

            kits.add(kit1, kit2, kit3);

            catalogService.saveAll(
                    accessories, books, equipment, fabrics, fillers,
                    giftCertificates, patterns, sewingThreads, tools, yarns, kits
            );

        };
    }
}
