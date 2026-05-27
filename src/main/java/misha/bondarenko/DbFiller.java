package misha.bondarenko;

import misha.bondarenko.entities.products.*;
import misha.bondarenko.enums.MeasureUnit;
import misha.bondarenko.services.CatalogService;
import misha.bondarenko.services.ItemService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Configuration
public class DbFiller {

    @Bean
    @Transactional
    public CommandLineRunner fillDatabase(CatalogService catalogService, ItemService itemService) {
        boolean shouldWork = true;
        if (!shouldWork) return args -> {};

        return args -> {
            // Clear database
            itemService.deleteAll();
            catalogService.deleteAll();

            // Fill with catalogs and products

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

            // --- 1. Аксесуари (Accessory) - 9 шт ---
            accessories.add(
                    new Accessory(null, "ART-301", "Ґудзик пластиковий 15мм", "Декоративний ґудзик для кардиганів",
                            new BigDecimal("15.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 500, true, "/images/btn1.jpg",
                            "Бабуся Надія ТМ", "Prym", "Україна", "Гудзик", "Пластик", "15мм", "Чорний"),
                    new Accessory(null, "ART-302", "Маркер для петель", "Кольоровий пластиковий маркер для зручного в'язання",
                            new BigDecimal("85.00"), new BigDecimal("0.05"), MeasureUnit.UNIT, 200, true, "/images/markers.jpg",
                            "В'язальний Рай", "KnitPro", "Україна", "Маркери петель", "Пластик", "Універсальний", "Мікс"),
                    new Accessory(null, "ART-303", "Гудзик випуклий", "Гудзик з випуклою формою для прикрашання виробів",
                            new BigDecimal("30.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 300, true, "/images/needles.jpg",
                            "ШвачкаОпт", "Prym", "Україна", "Гудзик", "Пластик", "10мм", "Асорті"),
                    new Accessory(null, "ART-304", "Бантик іграшковий", "Гарний бантик для прикрашання іграшок",
                            new BigDecimal("30.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 200, false, "/images/needles.jpg",
                            "В'язальний Рай", "KnitPro", "Україна","Бантик", "Тканина", "Асорті", "Сріблястий"),
                    new Accessory(null, "ART-305", "Маркер-кільце", "Металеві кільця для петель",
                            new BigDecimal("120.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 0, false, "/images/markers2.jpg",
                            "ШвачкаОпт", "Prym", "Німеччина", "Маркери петель", "Метал", "Універсальний", "Срібний"),
                    new Accessory(null, "ART-306", "Блискавка спіральна 20см", "Надійна блискавка для спідниць",
                            new BigDecimal("18.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 400, true, "/images/zipper.jpg",
                            "БлискавкаОпт", "YKK", "Японія", "Блискавка", "Нейлон/Пластик", "20см", "Чорний"),
                    new Accessory(null, "ART-307", "Блискавка потайна 50см", "Потайна блискавка для суконь",
                            new BigDecimal("25.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 0, false, "/images/zipper2.jpg",
                            "БлискавкаОпт", "YKK", "Японія", "Блискавка", "Нейлон", "50см", "Білий"),
                    new Accessory(null, "ART-308", "Гудзик металевий джинсовий", "Міцний гудзик для джинсового одягу",
                            new BigDecimal("12.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 150, true, "/images/btn2.jpg",
                            "ШвачкаОпт", "Prym", "Німеччина", "Гудзик", "Метал", "17мм", "Бронза"),
                    new Accessory(null, "ART-309", "Нашивка 'Handmade'", "Шкіряна бірка для авторських виробів",
                            new BigDecimal("20.00"), new BigDecimal("0.10"), MeasureUnit.UNIT, 300, true, "/images/label.jpg",
                            "В'язальний Рай", "HandmadeClub", "Україна", "Бірка", "Екошкіра", "3x1см", "Коричневий"),
                    new Accessory(null, "ART-310", "Нашивка 'Handmade'", "Пластикова бірка для авторських виробів",
                            new BigDecimal("15.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 200, true, "/images/label.jpg",
                            "В'язальний Рай", "HandmadeClub", "Україна", "Бірка", "Пластик", "3x1см", "білий"),
                    new Accessory(null, "ART-311", "Гудзик металевий", "Металевий гудзик для одежі. Міцний, гарний та надійний",
                            new BigDecimal("30.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 130, true, "/images/label.jpg",
                            "Бабуся Надія ТМ", "Prym", "Туреччина", "Гудзик", "залізо", "10мм", "металевий срібляний")

            );

            // --- 2. Книги (Book) - 9 шт ---
            books.add(
                    new Book(null, "Велика енциклопедія в'язання", "Детальний посібник з безліччю схем",
                            new BigDecimal("450.00"), new BigDecimal("0.10"), MeasureUnit.UNIT, 20, true, "/images/book1.jpg",
                            "ART-401", "Книготорг", "КСД", "Україна", "Елізабет Кент", "КСД", "978-617-12", 280, 2024),
                    new Book(null, "В'яжемо іграшки амігурумі", "Схеми милих іграшок",
                            new BigDecimal("380.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 15, true, "/images/book2.jpg",
                            "ART-402", "Книготорг", "Віват", "Україна", "Марія Іванова", "Віват", "978-966-982", 200, 2023),
                    new Book(null, "Шиття для початківців Knit&Sew", "Основи створення одягу",
                            new BigDecimal("520.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 10, false, "/images/book3.jpg",
                            "ART-403", "Книготорг", "Наш Формат", "Велика Британія", "Джейн Сміт", "Наш Формат", "978-617-78", 320, 2022),
                    new Book(null, "Шиття для майстринь Knit&Sew", "Просунуті способи шиття одежі",
                            new BigDecimal("600.00"), new BigDecimal("0.2"), MeasureUnit.UNIT, 9, true, "/images/book3.jpg",
                            "ART-404", "Книготорг", "Наш Формат", "Україна", "Марія Іванова", "Наш Формат", "978-812-09", 400, 2020),
                    new Book(null, "Вишивка хрестиком: пейзажі", "Неймовірні пейзажі у вишивці",
                            new BigDecimal("300.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 0, false, "/images/book4.jpg",
                            "ART-405", "Книготорг", "КСД", "Україна", "Олена Коваленко", "КСД", "978-617-15", 150, 2021),
                    new Book(null, "В'язання шкарпеток 2.0", "Сучасні техніки в'язання шкарпеток",
                            new BigDecimal("280.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 30, true, "/images/book5.jpg",
                            "ART-406", "Книготорг", "Віват", "Україна", "Стефані Перл", "Віват", "978-966-999", 120, 2023),
                    new Book(null, "Моделювання спідниць", "Від базової до складної форми",
                            new BigDecimal("410.00"), new BigDecimal("0.15"), MeasureUnit.UNIT, 0, false, "/images/book6.jpg",
                            "ART-407", "Книготорг", "Основи", "Україна", "Тетяна Бойко", "Основи", "978-966-500", 180, 2019),
                    new Book(null, "Амігурумі для профі", "Складні конструкції іграшок",
                            new BigDecimal("550.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 12, true, "/images/book7.jpg",
                            "ART-408", "Книготорг", "Віват", "Україна", "Марія Іванова", "Віват", "978-966-123", 250, 2024),
                    new Book(null, "Сучасне макраме", "Стильний декор для дому",
                            new BigDecimal("350.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 25, true, "/images/book8.jpg",
                            "ART-409", "Книготорг", "Наш Формат", "Україна", "Анна Ткач", "Наш Формат", "978-617-100", 160, 2022)
            );

            // --- 3. Обладнання (Equipment) - 9 шт ---
            equipment.add(
                    new Equipment(null, "Швейна машина Singer 23", "Надійна електромеханічна машина",
                            new BigDecimal("5500.00"), new BigDecimal("0.05"), MeasureUnit.UNIT, 5, true, "/images/eq1.jpg",
                            "ART-501", "ТехноШвачка", "Singer", "Німеччина", "Швейна машина", 24, 70, "40x30x20 см", 6.5, 23),
                    new Equipment(null, "Оверлок Janome 4-нитковий", "Професійний оверлок для країв",
                            new BigDecimal("7200.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 3, true, "/images/eq2.jpg",
                            "ART-502", "ТехноШвачка", "Janome", "Німеччина", "Оверлок", 12, 90, "35x35x30 см", 7.0, 4),
                    new Equipment(null, "Швейно-вишивальна машина Brother", "Багатофункціональний пристрій",
                            new BigDecimal("9800.00"), new BigDecimal("0.10"), MeasureUnit.UNIT, 2, true, "/images/eq3.jpg",
                            "ART-503", "ТехноШвачка", "Brother", "Німеччина", "Швейно-вишивальна", 36, 120, "50x40x30 см", 10.0, 80),
                    new Equipment(null, "В'язальна машина Silver Reed", "Перфокарткова в'язальна машина 5 класу",
                            new BigDecimal("35000.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 0, false, "/images/eq4.jpg",
                            "ART-504", "В'язальний Рай", "Silver Reed", "Японія", "В'язальна машина", 200, 0, "110x20x10 см", 15.0, 12),
                    new Equipment(null, "Прасувальна система Laurastar", "Професійна прасувальна система",
                            new BigDecimal("25000.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 0, false, "/images/eq5.jpg",
                            "ART-505", "ТехноШвачка", "Laurastar", "Швейцарія", "Прасувальна система", 5, 2200, "137x42x20 см", 19.0, 24),
                    new Equipment(null, "Розпошивальна машина Minerva", "Для обробки трикотажних виробів",
                            new BigDecimal("6800.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 4, true, "/images/eq6.jpg",
                            "ART-506", "ТехноШвачка", "Minerva", "Тайвань", "Розпошивальна машина", 15, 100, "40x35x30 см", 8.0, 12),
                    new Equipment(null, "Швейна машина Janome 419S", "Класична надійна модель",
                            new BigDecimal("6200.00"), new BigDecimal("0.05"), MeasureUnit.UNIT, 7, true, "/images/eq7.jpg",
                            "ART-507", "ТехноШвачка", "Janome", "Японія", "Швейна машина", 19, 60, "40x30x18 см", 7.5, 24),
                    new Equipment(null, "Оверлок Brother 1034D", "Популярний оверлок для дому",
                            new BigDecimal("8500.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 2, true, "/images/eq8.jpg",
                            "ART-508", "ТехноШвачка", "Brother", "Японія", "Оверлок", 10, 100, "30x30x28 см", 6.5, 12),
                    new Equipment(null, "Міні швейна машина дитяча", "Безпечна модель для навчання",
                            new BigDecimal("1200.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 10, true, "/images/eq9.jpg",
                            "ART-509", "ТехноШвачка", "SewMini", "Китай", "Швейна машина", 4, 10, "20x15x10 см", 1.5, 6)
            );

            // --- 4. Тканини (Fabric) - 9 шт ---
            fabrics.add(
                    new Fabric(null, "Бавовна біла натуральна", "Ідеальна для пошиття літніх речей",
                            new BigDecimal("180.00"), BigDecimal.ZERO, MeasureUnit.METER, 100, true, "/images/fab1.jpg",
                            "ART-601", "ТекстильОпт", "БавовнаТрейд", "Туреччина", "Бавовна", "100% бавовна", 150, 130, "Білий"),
                    new Fabric(null, "Льон пом'якшений сірий", "Екологічний матеріал для суконь та сорочок",
                            new BigDecimal("290.00"), new BigDecimal("0.05"), MeasureUnit.METER, 50, true, "/images/fab2.jpg",
                            "ART-602", "ТекстильОпт", "ЛьонПлюс", "Туреччина" ,"Льон", "100% льон", 140, 180, "Сірий"),
                    new Fabric(null, "Шовк натуральний червоний", "Розкішний шовк преміум-класу",
                            new BigDecimal("850.00"), BigDecimal.ZERO, MeasureUnit.METER, 30, true, "/images/fab3.jpg",
                            "ART-603", "ТекстильОпт", "ШовкСтайл", "Туреччина","Шовк", "100% шовк", 110, 80, "Червоний"),
                    new Fabric(null, "Фліс синій теплий", "М'який фліс для кофт та пледів",
                            new BigDecimal("220.00"), BigDecimal.ZERO, MeasureUnit.METER, 80, true, "/images/fab4.jpg",
                            "ART-604", "ТекстильОпт", "PolarFleece", "Корея", "Фліс", "100% поліестер", 150, 250, "Синій"),
                    new Fabric(null, "Денім класичний", "Щільна джинсова тканина",
                            new BigDecimal("350.00"), BigDecimal.ZERO, MeasureUnit.METER, 0, false, "/images/fab5.jpg",
                            "ART-605", "ТекстильОпт", "DenimCo", "Туреччина", "Денім", "95% бавовна, 5% еластан", 145, 300, "Індиго"),
                    new Fabric(null, "Трикотаж кулір пудра", "Тонкий трикотаж для футболок",
                            new BigDecimal("190.00"), BigDecimal.ZERO, MeasureUnit.METER, 0, false, "/images/fab6.jpg",
                            "ART-606", "ТекстильОпт", "CottonMix", "Туреччина", "Трикотаж", "100% бавовна", 180, 150, "Пудра"),
                    new Fabric(null, "Фатин середньої жорсткості", "Для пишних спідниць та декору",
                            new BigDecimal("90.00"), new BigDecimal("0.10"), MeasureUnit.METER, 200, true, "/images/fab7.jpg",
                            "ART-607", "ТекстильОпт", "FatinLux", "Китай", "Фатин", "100% нейлон", 300, 30, "Білий"),
                    new Fabric(null, "Велюр мармуровий", "Стильний велюр для костюмів",
                            new BigDecimal("280.00"), BigDecimal.ZERO, MeasureUnit.METER, 45, true, "/images/fab8.jpg",
                            "ART-608", "ТекстильОпт", "VelourTex", "Корея", "Велюр", "90% поліестер, 10% спандекс", 150, 220, "Чорний"),
                    new Fabric(null, "Муслін 2-шаровий дитячий", "Легка тканина для пелюшок та одягу",
                            new BigDecimal("210.00"), BigDecimal.ZERO, MeasureUnit.METER, 60, true, "/images/fab9.jpg",
                            "ART-609", "ТекстильОпт", "BabyTex", "Туреччина", "Муслін", "100% бавовна", 135, 120, "М'ятний"),
                    new Fabric(null, "Велюр оксамитовий стрейч", "Підійде для виготовлення одягу для іграшок, пошиття колекційних суконь для ляльок, гаманців і фермуаров, так само підходить для обтягування домашніх меблів та аксесуарів, пошиття подушок.",
                            new BigDecimal("60.00"), BigDecimal.ZERO, MeasureUnit.METER, 70, true, "/images/fab9.jpg",
                            "ART-610", "ТекстильОпт", "BabyTex", "Україна", "Велюр", "бавовна 70%, поліестер та еластан 30%", 135, 120, "синій")

            );

            // --- 5. Наповнювачі (Filler) - 9 шт ---
            fillers.add(
                    new Filler(null, "Холофайбер гіпоалергенний 1 кг", "М'який наповнювач для іграшок та подушок",
                            new BigDecimal("150.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 40, true, "/images/fil1.jpg",
                            "ART-701", "ЕкоСинтетика", "Холофайбер", "Україна", "Холофайбер", 100, true, 1.0),
                    new Filler(null, "Синтепух м'який 0.5 кг", "Легкий об'ємний наповнювач",
                            new BigDecimal("85.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 60, true, "/images/fil2.jpg",
                            "ART-702", "ЕкоСинтетика", "Синтепух", "Україна", "Синтепух", 150, false, 0.5),
                    new Filler(null, "Флізелін клейовий відривний", "Матеріал для ущільнення тканин",
                            new BigDecimal("45.00"), new BigDecimal("0.10"), MeasureUnit.METER, 100, true, "/images/fil3.jpg",
                            "ART-703", "УкрДублерин", "Флізелін", "Україна","Флізелін клейовий", 40, false, 0.1),
                    new Filler(null, "Вата бавовняна 1кг", "Екологічний наповнювач 100% бавовна",
                            new BigDecimal("120.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 0, false, "/images/fil4.jpg",
                            "ART-704", "ЕкоСинтетика", "ЕкоВата", "Україна", "Вата", 200, true, 1.0),
                    new Filler(null, "Дублерин тканинний жорсткий", "Для комірів та манжетів",
                            new BigDecimal("95.00"), BigDecimal.ZERO, MeasureUnit.METER, 80, true, "/images/fil5.jpg",
                            "ART-705", "УкрДублерин", "Дублерин", "Україна", "Дублерин", 150, false, 0.2),
                    new Filler(null, "Гранулят скляний для іграшок", "Обтяжувач для антистрес іграшок",
                            new BigDecimal("250.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 0, false, "/images/fil6.jpg",
                            "ART-706", "В'язальний Рай", "GlassBeads", "Чехія", "Гранулят", 800, true, 1.0),
                    new Filler(null, "Поролон листовий 10мм", "Для меблів та об'ємних деталей",
                            new BigDecimal("110.00"), BigDecimal.ZERO, MeasureUnit.METER, 30, true, "/images/fil7.jpg",
                            "ART-707", "ЕкоСинтетика", "ПоролонСтандарт", "Україна", "Поролон", 22, false, 0.5),
                    new Filler(null, "Синтепон 100 г/м2", "Тонкий рулонний утеплювач",
                            new BigDecimal("35.00"), BigDecimal.ZERO, MeasureUnit.METER, 150, true, "/images/fil8.jpg",
                            "ART-708", "ЕкоСинтетика", "Синтепон", "Україна", "Синтепон", 100, true, 0.1),
                    new Filler(null, "Синтепон 200 г/м2", "Щільний рулонний утеплювач для курток",
                            new BigDecimal("60.00"), new BigDecimal("0.05"), MeasureUnit.METER, 100, true, "/images/fil9.jpg",
                            "ART-709", "ЕкоСинтетика", "Синтепон", "Україна", "Синтепон", 200, true, 0.2)
            );

            // --- 6. Подарункові сертифікати (GiftCertificate) - 9 шт ---
            giftCertificates.add(
                    new GiftCertificate(null, "Сертифікат номіналом 500 грн", "Ідеальний подарунок для творчої людини",
                            new BigDecimal("500.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 999, true, "/images/cert1.jpg",
                            "ART-801", "Knit&Sew Офіс", "Knit&Sew", "Україна", "Електронний", 12, "Діє на всі товари"),
                    new GiftCertificate(null, "Сертифікат номіналом 1000 грн", "Чудовий подарунок до свята",
                            new BigDecimal("1000.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 999, true, "/images/cert2.jpg",
                            "ART-802", "Knit&Sew Офіс", "Knit&Sew", "Україна","Електронний", 12, "Діє на всі товари"),
                    new GiftCertificate(null, "Пластиковий сертифікат 2000 грн", "Фізична картка у гарному пакуванні",
                            new BigDecimal("2000.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 999, true, "/images/cert3.jpg",
                            "ART-803", "Knit&Sew Офіс", "Knit&Sew", "Україна","Пластиковий", 24, "Діє на всі товари"),
                    new GiftCertificate(null, "Сертифікат номіналом 3000 грн", "Великий подарунковий сертифікат",
                            new BigDecimal("3000.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 999, true, "/images/cert4.jpg",
                            "ART-804", "Knit&Sew Офіс", "Knit&Sew", "Україна", "Електронний", 12, "Діє на всі товари"),
                    new GiftCertificate(null, "Сертифікат номіналом 5000 грн", "Мега-подарунок для майстрині",
                            new BigDecimal("5000.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 0, false, "/images/cert5.jpg",
                            "ART-805", "Knit&Sew Офіс", "Knit&Sew", "Україна", "Електронний", 12, "Діє на всі товари"),
                    new GiftCertificate(null, "VIP Пластиковий сертифікат", "Ексклюзивна картка безстрокової дії",
                            new BigDecimal("10000.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 0, false, "/images/cert6.jpg",
                            "ART-806", "Knit&Sew Офіс", "Knit&Sew", "Україна", "Пластиковий", 120, "Безстроковий"),
                    new GiftCertificate(null, "Сертифікат на майстер-клас", "Оплата участі у будь-якому МК",
                            new BigDecimal("800.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 999, true, "/images/cert7.jpg",
                            "ART-807", "Knit&Sew Офіс", "Knit&Sew", "Україна", "Електронний", 6, "Тільки на МК"),
                    new GiftCertificate(null, "Сертифікат номіналом 200 грн", "Скромний знак уваги",
                            new BigDecimal("200.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 999, true, "/images/cert8.jpg",
                            "ART-808", "Knit&Sew Офіс", "Knit&Sew", "Україна", "Електронний", 12, "Діє на всі товари"),
                    new GiftCertificate(null, "Сертифікат номіналом 300 грн", "Приємний подарунок-бонус",
                            new BigDecimal("300.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 999, true, "/images/cert9.jpg",
                            "ART-809", "Knit&Sew Офіс", "Knit&Sew", "Україна", "Електронний", 12, "Діє на всі товари")
            );

            // --- 7. Схеми (Pattern) - 9 шт ---
            patterns.add(
                    new Pattern(null, "Схема светра 'Оверсайз'", "Детальний опис з відео-підказками",
                            new BigDecimal("150.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 999, true, "/images/pat1.jpg",
                            "ART-901", "KnitDesign", "KnitDesign", "Україна", "Анна Петрова", "Середній", "Українська", "PDF"),
                    new Pattern(null, "Схема іграшки 'Ведмедик'", "Схема для в'язання гачком (амігурумі)",
                            new BigDecimal("90.00"), new BigDecimal("0.10"), MeasureUnit.UNIT, 999, true, "/images/pat2.jpg",
                            "ART-902", "ToyMaker", "ToyMaker", "Україна", "Марія Іванова", "Початковий", "Українська", "PDF"),
                    new Pattern(null, "Викрійка літньої сукні Burda", "Офіційна паперова викрійка",
                            new BigDecimal("250.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 50, true, "/images/pat3.jpg",
                            "ART-903", "SewingPatterns", "SewingPatterns", "Україна", "Burda", "Складний", "Англійська", "Друкований"),
                    new Pattern(null, "Схема кардигану 'Осінь'", "Покрокова інструкція для в'язання спицями",
                            new BigDecimal("180.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 999, true, "/images/pat4.jpg",
                            "ART-904", "KnitDesign", "KnitDesign", "Україна", "Анна Петрова", "Середній", "Українська", "PDF"),
                    new Pattern(null, "Схема шкарпеток з жакардом", "Складна схема для любителів орнаментів",
                            new BigDecimal("120.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 0, false, "/images/pat5.jpg",
                            "ART-905", "SocksMaster", "SocksMaster", "Польща", "Ольга Новак", "Складний", "Англійська", "PDF"),
                    new Pattern(null, "Викрійка класичних штанів", "Базова паперова викрійка брюк",
                            new BigDecimal("200.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 30, true, "/images/pat6.jpg",
                            "ART-906", "SewingPatterns", "SewingPatterns", "Україна", "Burda", "Середній", "Українська", "Друкований"),
                    new Pattern(null, "Викрійка пальто оверсайз", "Складне моделювання верхнього одягу",
                            new BigDecimal("350.00"), new BigDecimal("0.05"), MeasureUnit.UNIT, 0, false, "/images/pat7.jpg",
                            "ART-907", "SewingPatterns", "Vogue", "США", "Vogue Patterns", "Професійний", "Англійська", "Друкований"),
                    new Pattern(null, "Схема пледа гачком", "Схема з мотивів 'Бабусин квадрат'",
                            new BigDecimal("80.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 999, true, "/images/pat8.jpg",
                            "ART-908", "ToyMaker", "ToyMaker", "Україна", "Ірина Бойко", "Початковий", "Українська", "PDF"),
                    new Pattern(null, "Схема зимової шапки", "Легка шапка бріош",
                            new BigDecimal("100.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 999, true, "/images/pat9.jpg",
                            "ART-909", "KnitDesign", "KnitDesign", "Україна", "Анна Петрова", "Середній", "Українська", "PDF")
            );

            // --- 8. Нитки для шиття (SewingThread) - 9 шт ---
            SewingThread thr1 = new SewingThread(null, "",
                    "Універсальні міцні нитки", new BigDecimal("75.00"), BigDecimal.ZERO, MeasureUnit.METER, 300, true,
                    "/images/thr1.jpg", "ART-1001", "НиткиОпт", "Gutermann", "Австрія", "Універсальна", "100% поліестер", "№40", 200, "Чорний");
            SewingThread thr2 = new SewingThread(null, "",
                    "Універсальні міцні нитки", new BigDecimal("75.00"), BigDecimal.ZERO, MeasureUnit.METER, 300, true,
                    "/images/thr2.jpg", "ART-1002", "НиткиОпт", "Gutermann", "Австрія", "Універсальна", "100% поліестер", "№40", 200, "Білий");
            SewingThread thr3 = new SewingThread(null, "",
                    "Металізована нитка для вишивки", new BigDecimal("120.00"), new BigDecimal("0.05"), MeasureUnit.METER, 150, true,
                    "/images/thr3.jpg", "ART-1003", "НиткиОпт", "Madeira", "Австрія", "Вишивальна", "Віскоза/Металік", "№40", 1000, "Золотий");
            SewingThread thr4= new SewingThread(null, "",
                    "Металізована нитка для вишивки", new BigDecimal("100.00"), new BigDecimal("0.05"), MeasureUnit.METER, 150, true,
                    "/images/thr3.jpg", "ART-1004", "НиткиОпт", "Madeira", "Австрія", "Вишивальна", "Віскоза/Металік", "№40", 1000, "Срібний");
            SewingThread thr5 = new SewingThread(null, "",
                    "Особливо міцні нитки для важких тканин", new BigDecimal("85.00"), BigDecimal.ZERO, MeasureUnit.METER, 200, true,
                    "/images/thr4.jpg", "ART-1005", "НиткиОпт", "Ariadna", "Польща", "Армована", "Поліестер", "№30", 200, "Червоний");
            SewingThread thr6 = new SewingThread(null, "",
                    "Особливо міцні нитки для джинсу", new BigDecimal("85.00"), BigDecimal.ZERO, MeasureUnit.METER, 0, false,
                    "/images/thr5.jpg", "ART-1006", "НиткиОпт", "Ariadna", "Польща", "Армована", "Поліестер", "№30", 200, "Синій");
            SewingThread thr7 = new SewingThread(null, "",
                    "М'які нитки для оверлока", new BigDecimal("150.00"), BigDecimal.ZERO, MeasureUnit.METER, 120, true,
                    "/images/thr6.jpg", "ART-1007", "НиткиОпт", "Euron", "Китай", "Оверлочна", "100% поліестер", "№150", 5000, "Білий");
            SewingThread thr8 = new SewingThread(null, "",
                    "М'які нитки для оверлока", new BigDecimal("150.00"), BigDecimal.ZERO, MeasureUnit.METER, 0, false,
                    "/images/thr7.jpg", "ART-1008", "НиткиОпт", "Euron", "Китай", "Оверлочна", "100% поліестер", "№150", 5000, "Чорний");
            SewingThread thr9 = new SewingThread(null, "",
                    "Спеціальні товсті нитки для відстрочки джинсів", new BigDecimal("90.00"), BigDecimal.ZERO, MeasureUnit.METER, 80, true,
                    "/images/thr8.jpg", "ART-1009", "НиткиОпт", "Gutermann", "Австрія", "Джинсова", "100% поліестер", "№30", 100, "Жовтий");

            sewingThreads.add(thr1, thr2, thr3, thr4, thr5, thr6, thr7, thr8, thr9);

            // --- 9. Інструменти (Tool) - 9 шт ---
            Tool tool1 = new Tool(null, "Гачок алюмінієвий 4мм", "Зручний гачок для пряжі",
                    new BigDecimal("56.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 190, true, "/images/plugs/tool plug.png",
                    "ART-1101", "Бабуся Надія ТМ", "KnitPro", "Німеччина", "Гачок", "Алюміній", "4мм");
            Tool tool2 = new Tool(null, "Спиці кругові KnitPro 3.5мм", "Дерев'яні спиці на тросику",
                    new BigDecimal("250.00"), new BigDecimal("0.10"), MeasureUnit.UNIT, 50, false, "/images/plugs/tool plug.png",
                    "ART-1102", "В'язальний Рай", "KnitPro", "Німеччина", "Спиці кругові", "Дерево", "3.5мм");
            Tool tool3 = new Tool(null, "Ножиці для вишивання Prym", "Гострі кравецькі ножиці",
                    new BigDecimal("80.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 120, true, "/images/plugs/tool plug.png",
                    "ART-1103", "ШвачкаОпт", "Prym", "Україна","Ножиці", "Сталь", "13см");
            Tool tool4 = new Tool(null, "Сніпер", "Сніпер для розпорування швів",
                    new BigDecimal("60.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 120, true, "/images/plugs/tool plug.png",
                    "ART-1104", "ШвачкаОпт", "Prym", "Україна","Сніпер", "Сталь, пластик", "10см");
            Tool tool5 = new Tool(null, "Спиці прямі алюмінієві 4мм", "Класичні прямі спиці для шарфів",
                    new BigDecimal("75.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 200, true, "/images/plugs/tool plug.png",
                    "ART-1105", "Бабуся Надія ТМ", "KnitPro", "Німеччина", "Спиці прямі", "Алюміній", "4мм");
            Tool tool6 = new Tool(null, "Спиці панчішні бамбукові 2.5мм", "Набір з 5 спиць для шкарпеток",
                    new BigDecimal("180.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 0, false, "/images/plugs/tool plug.png",
                    "ART-1106", "В'язальний Рай", "KnitPro", "Індія", "Спиці панчішні", "Бамбук", "2.5мм");
            Tool tool7 = new Tool(null, "Сантиметрова стрічка 150см", "Двостороння гнучка стрічка",
                    new BigDecimal("45.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 300, true, "/images/plugs/tool plug.png",
                    "ART-1107", "ШвачкаОпт", "Prym", "Німеччина", "Вимірювальний інструмент", "Скловолокно", "150см");
            Tool tool8 = new Tool(null, "Набір голок для ручного шиття", "Голки різного розміру, 20 шт",
                    new BigDecimal("50.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 0, false, "/images/plugs/tool plug.png",
                    "ART-1108", "ШвачкаОпт", "Prym", "Німеччина", "Голки", "Сталь", "Асорті");
            Tool tool9 = new Tool(null, "Крейда кравецька воскова", "Не кришиться, легко змивається",
                    new BigDecimal("25.00"), BigDecimal.ZERO, MeasureUnit.UNIT, 150, true, "/images/plugs/tool plug.png",
                    "ART-1109", "ШвачкаОпт", "Prym", "Німеччина", "Розмітка", "Віск", "Квадрат");

            tools.add(tool1, tool2, tool3, tool4, tool5, tool6, tool7, tool8, tool9);

            // --- 10. Пряжа (Yarn) - 9 шт ---
            Yarn yarn1 = new Yarn(null, "Пряжа Alize Lanagold Бордова", "Класична напіввовняна пряжа",
                    new BigDecimal("120.00"), new BigDecimal("0.08"), MeasureUnit.SKEIN, 350, true, "/images/plugs/yarn plug.png",
                    "ART-1201", "ЯрнОптТорг", "Alize", "Туреччина","51% акрил, 49% вовна", 240, 100, "LOT-88210", "Бордовий", "Спиці 4-6 мм");
            Yarn yarn2 = new Yarn(null, "Пряжа Alize Lanagold Синя", "Класична напіввовняна пряжа",
                    new BigDecimal("120.00"), BigDecimal.ZERO, MeasureUnit.SKEIN, 350, true, "/images/plugs/tool plug.png",
                    "ART-1202", "ЯрнОптТорг", "Alize", "Туреччина","51% акрил, 49% вовна", 240, 100, "LOT-88210", "Синій", "Спиці 4-6 мм");
            Yarn yarn3 = new Yarn(null, "Пряжа YarnArt Jeans Біла", "Бавовняна пряжа для літніх речей",
                    new BigDecimal("150.00"), new BigDecimal("0.10"), MeasureUnit.SKEIN, 200, true, "/images/plugs/yarn plug.png",
                    "ART-1203", "ПряжаТрейд", "YarnArt", "Туреччина", "55% бавовна, 45% поліакрил", 160, 50, "LOT-123", "Білий", "Гачок 2-3.5 мм");
            Yarn yarn4 = new Yarn(null, "Пряжа YarnArt Jeans Червона", "Бавовняна пряжа для речей",
                    new BigDecimal("150.00"), new BigDecimal("0.05"), MeasureUnit.SKEIN, 200, true, "/images/plugs/yarn plug.png",
                    "ART-1204", "ПряжаТрейд", "YarnArt", "Туреччина" ,"55% бавовна, 45% поліакрил", 160, 50, "LOT-124", "Червоний", "Гачок 2-3.5 мм");
            Yarn yarn5 = new Yarn(null, "Пряжа плюшева Himalaya Dolphin Baby М'ятна", "Дуже м'яка плюшева пряжа",
                    new BigDecimal("165.00"), BigDecimal.ZERO, MeasureUnit.SKEIN, 120, true, "/images/plugs/yarn plug.png",
                    "ART-1205", "ЯрнОптТорг", "Himalaya", "Туреччина", "100% мікрополіестер", 120, 100, "LOT-332", "М'ятний", "Гачок 4.5 мм");
            Yarn yarn6 = new Yarn(null, "Пряжа плюшева Himalaya Dolphin Baby Рожева", "Дуже м'яка плюшева пряжа",
                    new BigDecimal("165.00"), BigDecimal.ZERO, MeasureUnit.SKEIN, 0, false, "/images/plugs/yarn plug.png",
                    "ART-1206", "ЯрнОптТорг", "Himalaya", "Туреччина", "100% мікрополіестер", 120, 100, "LOT-333", "Рожевий", "Гачок 4.5 мм");
            Yarn yarn7 = new Yarn(null, "Пряжа мохер Alize Kid Royal Сіра", "Тонкий і пухнастий мохер",
                    new BigDecimal("130.00"), BigDecimal.ZERO, MeasureUnit.SKEIN, 90, true, "/images/plugs/yarn plug.png",
                    "ART-1207", "ЯрнОптТорг", "Alize", "Туреччина", "62% кід мохер, 38% поліамід", 500, 50, "LOT-111", "Сірий", "Спиці 2-6 мм");
            Yarn yarn8 = new Yarn(null, "Пряжа мохер Alize Kid Royal Чорна", "Тонкий і пухнастий мохер",
                    new BigDecimal("130.00"), BigDecimal.ZERO, MeasureUnit.SKEIN, 0, false, "/images/plugs/yarn plug.png",
                    "ART-1208", "ЯрнОптТорг", "Alize", "Туреччина", "62% кід мохер, 38% поліамід", 500, 50, "LOT-112", "Чорний", "Спиці 2-6 мм");
            Yarn yarn9 = new Yarn(null, "Пряжа шкарпеткова Alize Artisan", "Спеціальна зносостійка пряжа",
                    new BigDecimal("180.00"), BigDecimal.ZERO, MeasureUnit.SKEIN, 150, true, "/images/plugs/yarn plug.png",
                    "ART-1209", "ЯрнОптТорг", "Alize", "Туреччина", "75% вовна, 25% поліамід", 420, 100, "LOT-777", "Гірчичний", "Спиці 2-4 мм");

            yarns.add(yarn1, yarn2, yarn3, yarn4, yarn5, yarn6, yarn7, yarn8, yarn9);

            // --- 11. Набори (Kit) - 9 шт ---
            Kit kit1 = new Kit(null, "ART-1301", "Набір 'Старт в'язання'",
                    "Все необхідне для першого шарфа", BigDecimal.ZERO,
                    MeasureUnit.UNIT, 20, true, "/images/kit1.jpg");
            kit1.add(yarn1, 3); // 3 мотки пряжі
            kit1.add(tool2, 1); // 1 пара спиць


            Kit kit2 = new Kit(null, "ART-1302", "Набір 'Швачка-початківець'", "Базові нитки для шиття",
                    new BigDecimal("0.10"), MeasureUnit.UNIT, 15, true, "/images/kit2.jpg");
            kit2.add(thr1, 2); // 2 котушки чорних ниток
            kit2.add(thr2, 2); // 2 котушки білих ниток

            Kit kit3 = new Kit(null, "ART-1303", "Набір 'Велика іграшка'", "Пряжа та наповнювач для амігурумі",
                    BigDecimal.ZERO, MeasureUnit.UNIT, 10, true, "/images/kit3.jpg");
            kit3.add(yarn3, 5); // 5 мотків пряжі
            kit3.add(tool1, 1); // гачок

            Kit kit4 = new Kit(null, "ART-1304", "Набір 'Теплі шкарпетки'", "Пряжа та спиці для теплих шкарпеток",
                    BigDecimal.ZERO, MeasureUnit.UNIT, 25, true, "/images/kit4.jpg");
            kit4.add(yarn9, 2);
            kit4.add(tool6, 1);

            Kit kit5 = new Kit(null, "ART-1305", "Набір 'Шапка і снуд'", "Все для зимового комплекту",
                    new BigDecimal("0.15"), MeasureUnit.UNIT, 0, false, "/images/kit5.jpg");
            kit5.add(yarn1, 4);
            kit5.add(tool5, 1);

            Kit kit6 = new Kit(null, "ART-1306", "Набір 'Літня сумка'", "Бавовняна пряжа та гачок",
                    BigDecimal.ZERO, MeasureUnit.UNIT, 12, true, "/images/kit6.jpg");
            kit6.add(yarn3, 4);
            kit6.add(yarn4, 2);
            kit6.add(tool1, 1);

            Kit kit7 = new Kit(null, "ART-1307", "Набір 'Плюшевий ведмедик'", "Плюшева пряжа та наповнювач",
                    BigDecimal.ZERO, MeasureUnit.UNIT, 0, false, "/images/kit7.jpg");
            kit7.add(yarn5, 3);
            kit7.add(yarn6, 1);

            Kit kit8 = new Kit(null, "ART-1308", "Набір 'Ексклюзивна вишивка'", "Золоті та срібні нитки",
                    BigDecimal.ZERO, MeasureUnit.UNIT, 18, true, "/images/kit8.jpg");
            kit8.add(thr3, 2);
            kit8.add(thr4, 2);

            Kit kit9 = new Kit(null, "ART-1309", "Набір 'Светр реглан'", "Мохер та спиці для невагомого светра",
                    new BigDecimal("0.20"), MeasureUnit.UNIT, 8, true, "/images/kit9.jpg");
            kit9.add(yarn7, 5);
            kit9.add(tool2, 1);

            kits.add(kit1, kit2, kit3, kit4, kit5, kit6, kit7, kit8, kit9);

            catalogService.saveAll(
                    yarns,
                    tools,
                    accessories,
                    fillers,
                    kits,
                    equipment,
                    fabrics,
                    patterns,
                    sewingThreads,
                    books,
                    giftCertificates
            );
        };
    }
}