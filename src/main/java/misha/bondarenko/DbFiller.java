package misha.bondarenko;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbFiller {

    @Bean
    public CommandLineRunner fillDatabase() {
        boolean shouldWork = true;
        if (!shouldWork) return null;

        return args -> {
            // TODO: fill DB with basic products and categories
        };

    }
}
