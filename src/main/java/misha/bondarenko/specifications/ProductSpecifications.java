package misha.bondarenko.specifications;

import jakarta.persistence.criteria.Predicate;
import misha.bondarenko.records.filters.ProductFilter;
import misha.bondarenko.entities.products.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Клас для генерації динамічних SQL-запитів фільтрації товарів.
 */
public class ProductSpecifications {

    public static Specification<Item> withFilter(ProductFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // === 1. Загальні параметри ===
            if (filter.minPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), filter.minPrice()));
            }
            if (filter.maxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), filter.maxPrice()));
            }
            if (filter.isAvailable() != null) {
                predicates.add(cb.equal(root.get("isAvailable"), filter.isAvailable()));
            }
            if (filter.brand() != null && !filter.brand().isBlank()) {
                predicates.add(cb.equal(root.get("brand"), filter.brand()));
            }
            if (filter.supplier() != null && !filter.supplier().isBlank()) {
                predicates.add(cb.equal(root.get("supplier"), filter.supplier()));
            }

            // === 2. Спільні специфічні параметри ===
            if (filter.color() != null && !filter.color().isBlank()) {
                predicates.add(cb.or(
                        cb.equal(cb.treat(root, Fabric.class).get("color"), filter.color()),
                        cb.equal(cb.treat(root, Yarn.class).get("color"), filter.color()),
                        cb.equal(cb.treat(root, SewingThread.class).get("color"), filter.color()),
                        cb.equal(cb.treat(root, Accessory.class).get("color"), filter.color())
                ));
            }

            if (filter.material() != null && !filter.material().isBlank()) {
                predicates.add(cb.or(
                        cb.equal(cb.treat(root, Tool.class).get("material"), filter.material()),
                        cb.equal(cb.treat(root, Accessory.class).get("material"), filter.material())
                ));
            }

            if (filter.size() != null && !filter.size().isBlank()) {
                predicates.add(cb.or(
                        cb.equal(cb.treat(root, Tool.class).get("size"), filter.size()),
                        cb.equal(cb.treat(root, Accessory.class).get("size"), filter.size())
                ));
            }

            if (filter.composition() != null && !filter.composition().isBlank()) {
                String pattern = "%" + filter.composition().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(cb.treat(root, Fabric.class).get("composition")), pattern),
                        cb.like(cb.lower(cb.treat(root, SewingThread.class).get("composition")), pattern),
                        cb.like(cb.lower(cb.treat(root, Yarn.class).get("fiberContent")), pattern)
                ));
            }

            if (filter.minDensity() != null) {
                predicates.add(cb.or(
                        cb.greaterThanOrEqualTo(cb.treat(root, Fabric.class).get("density"), filter.minDensity()),
                        cb.greaterThanOrEqualTo(cb.treat(root, Filler.class).get("density"), filter.minDensity())
                ));
            }

            if (filter.maxDensity() != null) {
                predicates.add(cb.or(
                        cb.lessThanOrEqualTo(cb.treat(root, Fabric.class).get("density"), filter.maxDensity()),
                        cb.lessThanOrEqualTo(cb.treat(root, Filler.class).get("density"), filter.maxDensity())
                ));
            }

            // === 3. Унікальні параметри для конкретних категорій ===

            // Типи сутностей
            if (filter.accessoryType() != null && !filter.accessoryType().isBlank()) {
                predicates.add(cb.equal(cb.treat(root, Accessory.class).get("accessoryType"), filter.accessoryType()));
            }
            if (filter.fabricType() != null && !filter.fabricType().isBlank()) {
                predicates.add(cb.equal(cb.treat(root, Fabric.class).get("fabricType"), filter.fabricType()));
            }
            if (filter.fillerType() != null && !filter.fillerType().isBlank()) {
                predicates.add(cb.equal(cb.treat(root, Filler.class).get("fillerType"), filter.fillerType()));
            }
            if (filter.threadType() != null && !filter.threadType().isBlank()) {
                predicates.add(cb.equal(cb.treat(root, SewingThread.class).get("threadType"), filter.threadType()));
            }
            if (filter.toolType() != null && !filter.toolType().isBlank()) {
                predicates.add(cb.equal(cb.treat(root, Tool.class).get("toolType"), filter.toolType()));
            }
            if (filter.equipmentType() != null && !filter.equipmentType().isBlank()) {
                predicates.add(cb.equal(cb.treat(root, Equipment.class).get("equipmentType"), filter.equipmentType()));
            }
            if (filter.certificateType() != null && !filter.certificateType().isBlank()) {
                predicates.add(cb.equal(cb.treat(root, GiftCertificate.class).get("certificateType"), filter.certificateType()));
            }

            // Обладнання
            if (filter.minOperationsCount() != null) {
                predicates.add(cb.greaterThanOrEqualTo(cb.treat(root, Equipment.class).get("operationsCount"), filter.minOperationsCount()));
            }
            if (filter.maxWeightKg() != null) {
                predicates.add(cb.lessThanOrEqualTo(cb.treat(root, Equipment.class).get("weightKg"), filter.maxWeightKg()));
            }

            // Література та Схеми
            if (filter.author() != null && !filter.author().isBlank()) {
                predicates.add(cb.or(
                        cb.equal(cb.treat(root, Book.class).get("author"), filter.author()),
                        cb.equal(cb.treat(root, Pattern.class).get("author"), filter.author())
                ));
            }
            if (filter.publisher() != null && !filter.publisher().isBlank()) {
                predicates.add(cb.equal(cb.treat(root, Book.class).get("publisher"), filter.publisher()));
            }
            if (filter.publicationYear() != null) {
                predicates.add(cb.equal(cb.treat(root, Book.class).get("publicationYear"), filter.publicationYear()));
            }
            if (filter.difficultyLevel() != null && !filter.difficultyLevel().isBlank()) {
                predicates.add(cb.equal(cb.treat(root, Pattern.class).get("difficultyLevel"), filter.difficultyLevel()));
            }
            if (filter.format() != null && !filter.format().isBlank()) {
                predicates.add(cb.equal(cb.treat(root, Pattern.class).get("format"), filter.format()));
            }
            if (filter.language() != null && !filter.language().isBlank()) {
                predicates.add(cb.equal(cb.treat(root, Pattern.class).get("language"), filter.language()));
            }

            // Наповнювачі
            if (filter.isHypoallergenic() != null) {
                predicates.add(cb.equal(cb.treat(root, Filler.class).get("isHypoallergenic"), filter.isHypoallergenic()));
            }

            // Збираємо всі умови через AND
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}