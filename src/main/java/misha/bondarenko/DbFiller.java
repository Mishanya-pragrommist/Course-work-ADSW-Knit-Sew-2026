package misha.bondarenko;

import misha.bondarenko.entities.products.Catalog;
import misha.bondarenko.entities.products.Tool;
import misha.bondarenko.entities.products.Yarn;
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
            Catalog yarns = new Catalog(null, "Пряжа", "Пряжа для в'язання", "/images/image1.jpeg");
            Catalog tools = new Catalog(null, "Інструменти", "Інструменти для шиття, в'язання; також допоміжні приладдя", "/images/knitting-tools-table.jpg");
            Catalog fabrics = new Catalog(null, "Тканина", "Різна тканина для різних цілей", "/images/multi-color-fabric-texture-samples.jpg");
            Catalog patterns = new Catalog(null, "Схеми", "Схеми в'язання, шиття тощо", "/images/pattern.jpg");
            Catalog equipment = new Catalog(null, "Обладнання", "Швейні машинки та ще щось", "/images/sewing_machine.jpg");
            Catalog fillers = new Catalog(null, "Наповнювачі", "Синтепух, вата, пір'я - все для наповнення", "/images/filler.jpg");
            Catalog kits = new Catalog(null, "Набори", "Готові набори для в'язання та шиття", "/images/kit.jpg");

            yarns.add(
                    new Yarn(
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
                    ),
                    new Yarn(
                            true,
                            "YAR-CODE-02",
                            "ART-YAR-102",
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
                    ),
                    new Yarn(
                            true,
                            "YAR-CODE-03",
                            "ART-YAR-103",
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
                    )
            );

            tools.add(
                    new Tool(true,
                            "091203",
                            "Art. 091212",
                            "Бабуся Надія ТМ",
                            "ДебільніГачки",
                            190,
                            MeasureUnit.UNIT,
                            new BigDecimal("56.00"),
                            BigDecimal.ZERO,
                            "Гачок",
                            "алюміній",
                            "4мм"
                            ),
                    new Tool(true,
                            "54231",
                            "Art. 52315",
                            "Бабуся Надія ТМ",
                            "ДебільніГачки",
                            190,
                            MeasureUnit.UNIT,
                            new BigDecimal("56.00"),
                            BigDecimal.ZERO,
                            "Гачок",
                            "алюміній",
                            "4мм"
                    ),
                    new Tool(true,
                            "887464",
                            "Art. 12845",
                            "Бабуся Надія ТМ",
                            "ДебільніГачки",
                            190,
                            MeasureUnit.UNIT,
                            new BigDecimal("60.00"),
                            BigDecimal.ZERO,
                            "Гачок",
                            "алюміній",
                            "5мм"
                    )
            );

            catalogService.saveAll(yarns, tools, fabrics, patterns, equipment, fillers, kits);

            System.out.println("2) Отримуємо каталоги з БД\n");
            System.out.println(catalogService.getRootCatalogsString());
            System.out.println("\n" + itemService.renderItems());
            System.out.println("\n" + itemService.renderProducts());
            //catalogService.getRootCatalogs().forEach(catalog -> System.out.println(catalog.render("")));
        };

    }
}
