package misha.bondarenko;

import misha.bondarenko.entities.products.Catalog;
import misha.bondarenko.entities.products.Tool;
import misha.bondarenko.entities.products.Yarn;
import misha.bondarenko.enums.MeasureUnit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DbFiller {

    @Bean
    public CommandLineRunner fillDatabase() {
        boolean shouldWork = true;
        if (!shouldWork) return null;

        return args -> {
            Catalog yarns = new Catalog(null, "Пряжа", "Пряжа для в'язання");
            Catalog tools = new Catalog(null, "Інструменти", "Інструменти для шиття, в'язання; також допоміжні приладдя");
            Catalog fabrics = new Catalog(null, "Тканина", "Різна тканина для різних цілей");

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
                    )
            );

            tools.add(
                    new Tool(),

            );
        };

    }
}
