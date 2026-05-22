package misha.bondarenko.specifications;

import jakarta.persistence.criteria.Predicate;
import misha.bondarenko.records.filters.ProductFilter;
import misha.bondarenko.entities.products.*;
import misha.bondarenko.entities.products.Item;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Клас для генерації динамічних SQL-запитів фільтрації товарів.
 */
public class ProductSpecifications {

    // Змінено тип специфікації на Item, щоб включити Kit та Product
    public static Specification<Item> withFilter(ProductFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.minPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), filter.minPrice()));
            }
            if (filter.maxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), filter.maxPrice()));
            }

            if (filter.article() != null) {
                predicates.add(cb.like(root.get("article"), "%" + filter.article().toLowerCase() + "%"));
            }

            if (Boolean.TRUE.equals(filter.isAvailable())) {
                predicates.add(cb.equal(root.get("isAvailable"), true));
            }

            // Поля brand, supplier та country існують тільки в Product, тому приводимо root до Product
            if (filter.brand() != null && !filter.brand().isBlank()) {
                String brandQuery = "%" + filter.brand().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(cb.treat(root, Product.class).get("brand")), brandQuery));
            }
            if (filter.country() != null && !filter.country().isBlank()) {
                String countryQuery = "%" + filter.country().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(cb.treat(root, Product.class).get("country")), countryQuery));
            }
            if (filter.supplier() != null && !filter.supplier().isBlank()) {
                String supplierQuery = "%" + filter.supplier().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(cb.treat(root, Product.class).get("supplier")), supplierQuery));
            }

            // === 2. Спільні специфічні параметри (з частковим збігом без регістру) ===
            if (filter.color() != null && !filter.color().isBlank()) {
                String colorQuery = "%" + filter.color().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(cb.treat(root, Fabric.class).get("color")), colorQuery),
                        cb.like(cb.lower(cb.treat(root, Yarn.class).get("color")), colorQuery),
                        cb.like(cb.lower(cb.treat(root, SewingThread.class).get("color")), colorQuery),
                        cb.like(cb.lower(cb.treat(root, Accessory.class).get("color")), colorQuery)
                ));
            }
            // Material
            if (filter.material() != null && !filter.material().isBlank()) {
                String materialQuery = "%" + filter.material().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(cb.treat(root, Tool.class).get("material")), materialQuery),
                        cb.like(cb.lower(cb.treat(root, Accessory.class).get("material")), materialQuery)
                ));
            }
            // DyeLot
            if (filter.dyeLot() != null && !filter.dyeLot().isBlank()) {
                predicates.add(cb.like(cb.treat(root, Yarn.class).get("dyeLot"), "%" + filter.dyeLot().toLowerCase() + "%"));
            }

            if (filter.size() != null && !filter.size().isBlank()) {
                String sizeQuery = "%" + filter.size().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(cb.treat(root, Tool.class).get("size")), sizeQuery),
                        cb.like(cb.lower(cb.treat(root, Accessory.class).get("size")), sizeQuery)
                ));
            }

            if (filter.composition() != null && !filter.composition().isBlank()) {
                String compQuery = "%" + filter.composition().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(cb.treat(root, Fabric.class).get("composition")), compQuery),
                        cb.like(cb.lower(cb.treat(root, SewingThread.class).get("composition")), compQuery),
                        cb.like(cb.lower(cb.treat(root, Yarn.class).get("fiberContent")), compQuery)
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
                String queryType = "%" + filter.accessoryType().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(cb.treat(root, Accessory.class).get("accessoryType")), queryType));
            }
            if (filter.fabricType() != null && !filter.fabricType().isBlank()) {
                String queryType = "%" + filter.fabricType().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(cb.treat(root, Fabric.class).get("fabricType")), queryType));
            }
            if (filter.fillerType() != null && !filter.fillerType().isBlank()) {
                String queryType = "%" + filter.fillerType().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(cb.treat(root, Filler.class).get("fillerType")), queryType));
            }
            if (filter.threadType() != null && !filter.threadType().isBlank()) {
                String queryType = "%" + filter.threadType().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(cb.treat(root, SewingThread.class).get("threadType")), queryType));
            }
            if (filter.toolType() != null && !filter.toolType().isBlank()) {
                String queryType = "%" + filter.toolType().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(cb.treat(root, Tool.class).get("toolType")), queryType));
            }
            if (filter.equipmentType() != null && !filter.equipmentType().isBlank()) {
                String queryType = "%" + filter.equipmentType().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(cb.treat(root, Equipment.class).get("equipmentType")), queryType));
            }
            if (filter.certificateType() != null && !filter.certificateType().isBlank()) {
                String queryType = "%" + filter.certificateType().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(cb.treat(root, GiftCertificate.class).get("certificateType")), queryType));
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
                String authorQuery = "%" + filter.author().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(cb.treat(root, Book.class).get("author")), authorQuery),
                        cb.like(cb.lower(cb.treat(root, Pattern.class).get("author")), authorQuery)
                ));
            }
            if (filter.publisher() != null && !filter.publisher().isBlank()) {
                String publisherQuery = "%" + filter.publisher().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(cb.treat(root, Book.class).get("publisher")), publisherQuery));
            }
            if (filter.publicationYear() != null) {
                predicates.add(cb.equal(cb.treat(root, Book.class).get("publicationYear"), filter.publicationYear()));
            }
            if (filter.difficultyLevel() != null && !filter.difficultyLevel().isBlank()) {
                String diffQuery = "%" + filter.difficultyLevel().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(cb.treat(root, Pattern.class).get("difficultyLevel")), diffQuery));
            }
            if (filter.format() != null && !filter.format().isBlank()) {
                String formatQuery = "%" + filter.format().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(cb.treat(root, Pattern.class).get("format")), formatQuery));
            }
            if (filter.language() != null && !filter.language().isBlank()) {
                String langQuery = "%" + filter.language().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(cb.treat(root, Pattern.class).get("language")), langQuery));
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