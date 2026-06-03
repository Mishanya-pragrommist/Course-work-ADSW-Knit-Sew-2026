package misha.bondarenko.records.filters;

import java.math.BigDecimal;

/**
 * Фільтр для всіх можливих критеріїв фільтрації товарів у каталозі
 */
public record ProductFilter(
        // === Загальні параметри (Product / Kit) ===
        String name,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Boolean isAvailable,
        String brand,
        String supplier,
        String article,

        // === Спільні специфічні параметри ===
        String color,            // Для Fabric, Yarn, SewingThread, Accessory
        String material,         // Для Tool, Accessory
        String size,             // Для Tool, Accessory (діаметр, довжина тощо)
        String composition,      // Для Fabric, SewingThread, Yarn (fiberContent)
        String country,          // Для всіх товарів окрім наборів
        Integer minDensity,      // Для Fabric, Filler
        Integer maxDensity,      // Для Fabric, Filler

        // === Унікальні параметри для конкретних категорій ===
        // Типи сутностей
        String accessoryType,    // Accessory
        String fabricType,       // Fabric
        String fillerType,       // Filler
        String threadType,       // SewingThread
        String toolType,         // Tool
        String equipmentType,    // Equipment
        String certificateType,  // GiftCertificate
        String dyeLot,           // Yarn

        // Обладнання (Equipment)
        Integer maxWeightKg,     // Integer, бо може бути null

        // Література та Схеми (Book / Pattern)
        String author,           // Для Book, Pattern
        String publisher,        // Для Book
        Integer publicationYear, // Для Book
        String difficultyLevel,  // Для Pattern
        String format,           // Для Pattern
        String language,         // Для Pattern

        // Наповнювачі (Filler)
        Boolean isHypoallergenic

)
{ }
