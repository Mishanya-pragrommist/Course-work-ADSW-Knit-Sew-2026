package misha.bondarenko.entities.products;

import misha.bondarenko.enums.MeasureUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {
    private Accessory accessory;
    private Book book;
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
        // 1. Ініціалізація Фурнітури (Accessory)
        accessory = new Accessory(
                true,
                "ACC-CODE-01",
                "ART-ACC-881",
                "ТекстильОпт",
                "Prym",
                120,
                MeasureUnit.UNIT,
                new BigDecimal("45.00"),
                BigDecimal.ZERO, // без знижки
                "Ґудзик декоративний",
                "Дерево",
                "25 мм",
                "коричневий"
        );
        accessory.setId(1L);
        accessory.setName("Дерев'яний ґудзик Prym");
        accessory.setDescription("Декоративний ґудзик для в'язаних кардиганів");

        // 2. Ініціалізація Книги (Book)
        book = new Book(
                true,
                "BOOK-CODE-01",
                "ART-BOOK-102",
                "Книжковий Дистриб'ютор",
                "Видавництво КСД",
                15,
                MeasureUnit.UNIT,
                new BigDecimal("420.00"),
                new BigDecimal("0.10"),
                "В'язання спицями: Велика енциклопедія", // 10% знижки
                "Елізабет Кент",
                "КСД",
                "978-617-12-1234-5",
                280,
                2024);
        book.setId(2L);
        book.setDescription("Покрокові інструкції та 300 візерунків для в'язання");

        // 3. Ініціалізація Тканини (Fabric)
        fabric = new Fabric(
                true,
                "FAB-CODE-01",
                "ART-FAB-301",
                "ЛьонУкрТекстиль",
                "BrandTextile",
                250,
                MeasureUnit.METERS,
                new BigDecimal("220.00"),
                new BigDecimal("0.05"), // 5% знижки
                "Льон",
                "100% натуральний льон",
                150,
                180,
                "натуральний сірий"
        );
        fabric.setId(3L);
        fabric.setName("Натуральний пом'якшений льон");
        fabric.setDescription("Тканина чудово підходить для літнього одягу");

        // 4. Ініціалізація Наповнювача (Filler)
        filler = new Filler(
                true,
                "FIL-CODE-01",
                "ART-FIL-401",
                "ЕкоНаповнювачі",
                "Холофайбер Преміум",
                45,
                MeasureUnit.KG,
                new BigDecimal("180.00"),
                BigDecimal.ZERO,
                "Холофайбер",
                120,
                true, // Гіпоалергенний
                1.0
        );
        filler.setId(4L);
        filler.setName("Гіпоалергенний холофайбер");
        filler.setDescription("Високоякісний наповнювач для іграшок та подушок");

        // 5. Ініціалізація Сертифіката (GiftCertificate)
        giftCertificate = new GiftCertificate(
                true,
                "CERT-CODE-01",
                "ART-CERT-500",
                "Knit&Sew Офіс",
                "Knit&Sew",
                1000,
                MeasureUnit.UNIT,
                new BigDecimal("500.00"),
                BigDecimal.ZERO,
                "Електронний",
                12, // 12 місяців термін дії
                "Діє на весь асортимент офлайн та онлайн магазинів"
        );
        giftCertificate.setId(5L);
        giftCertificate.setName("Подарунковий сертифікат 500 грн");
        giftCertificate.setDescription("Електронний сертифікат з унікальним штрих-кодом");

        // 6. Ініціалізація Схеми/Інструкції (Pattern)
        pattern = new Pattern(
                true,
                "PAT-CODE-01",
                "ART-PAT-551",
                "KnitDesign Studio",
                "Petrenko Design",
                9999,
                MeasureUnit.UNIT,
                new BigDecimal("150.00"),
                BigDecimal.ZERO,
                "Ольга Петренко",
                "Середній",
                "Українська",
                "PDF"
        );
        pattern.setId(6L);
        pattern.setName("Схема в'язання светра 'Оверсайз'");
        pattern.setDescription("Детальний опис з відео-підказками складних моментів");

        // 7. Ініціалізація Швейних ниток (SewingThread)
        sewingThread = new SewingThread(
                true,
                "THR-CODE-01",
                "ART-THR-201",
                "НиткиОпт",
                "Gutermann",
                800,
                MeasureUnit.UNIT,
                new BigDecimal("85.00"),
                BigDecimal.ZERO,
                "Універсальна",
                "100% поліестер",
                "№40",
                200,
                "Чорний (col. 000)"
        );
        sewingThread.setId(7L);
        sewingThread.setName("Нитки швейні Gutermann №40");
        sewingThread.setDescription("Універсальні міцні нитки німецької якості");

        // 8. Ініціалізація Інструмента (Tool)
        tool = new Tool(
                true,
                "TOL-CODE-01",
                "ART-TOL-110",
                "В'язальний Рай",
                "ChiaoGoo",
                60,
                MeasureUnit.UNIT,
                new BigDecimal("490.00"),
                BigDecimal.ZERO,
                "Спиці кругові",
                "Хірургічна сталь",
                "3.5 мм (80 см)"
        );
        tool.setId(8L);
        tool.setName("Спиці кругові ChiaoGoo Red Lace 3.5 мм");
        tool.setDescription("Професійні спиці на супер-гнучкій металевій червоній волосіні");

        // 9. Ініціалізація Пряжі (Yarn)
        yarn = new Yarn(
                true,
                "YAR-CODE-01",
                "ART-YAR-101",
                "ЯрнОптТорг",
                "Alize",
                350,
                MeasureUnit.UNIT,
                new BigDecimal("120.00"),
                new BigDecimal("0.08"), // 8% знижки
                "51% акрил, 49% вовна",
                240,
                100,
                "LOT-88210",
                "Бордовий (col. 390)"
        );
        yarn.setId(9L);
        yarn.setName("Пряжа Alize Lanagold");
        yarn.setDescription("Класична напіввовняна пряжа середньої товщини");

        // 10. Ініціалізація Набору (Kit)
        // Для Kit використовуємо Long ID згідно з твоїм класом
        kit = new Kit(100L, "Творчий старт", "Повний набір матеріалів для початківця", "https://www.livemaster.ru/topic/2214489-vidy-pryazhi-plyusy-i-minusy-razlinoj-pryazhi",
                new BigDecimal("0.15")); // 15% загальна знижка набору
        kit.setName("Набір 'Творчий старт'");
        kit.setDescription("Ексклюзивний набір, що поєднує інструменти, тканину та нитки в одній коробці");

        // Наповнюємо набір компонентами та задаємо кількість штук кожного товару
        kit.add(tool, 1);         // 1 шт. спиць ChiaoGoo
        kit.add(yarn, 3);         // 3 мотки бордової пряжі Alize
        kit.add(accessory, 10);   // 10 дерев'яних ґудзиків Prym
        kit.add(sewingThread, 2); // 2 котушки чорних ниток Gutermann
    }

    @Test
    @DisplayName("Приклад назв елементів для відображення на вебсторінці")
    void renderNameTest() {
        List<Item> items = List.of(
                accessory,
                book,
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
        items.forEach(item -> System.out.println("\t" + item.renderName()));
    }

    @Test
    @DisplayName("")
    void renderProductCard() {

    }

}
