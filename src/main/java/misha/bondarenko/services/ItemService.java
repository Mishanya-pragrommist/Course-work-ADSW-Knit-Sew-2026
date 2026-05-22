package misha.bondarenko.services;

import misha.bondarenko.entities.products.*;
import misha.bondarenko.records.dto.ProductCardDto;
import misha.bondarenko.records.filters.ProductFilter;
import misha.bondarenko.repositories.CatalogRepository;
import misha.bondarenko.repositories.ItemRepository;
import misha.bondarenko.specifications.ProductSpecifications;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemService {
    private final CatalogRepository catalogRepository;
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository,
                       CatalogRepository catalogRepository) {
        this.itemRepository = itemRepository;
        this.catalogRepository = catalogRepository;
    }

    public Item findItemById(long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public Item findItemByName(String name) {
        return itemRepository.findByName(name).orElse(null);
    }

    public List<ProductCardDto> getProductCardsDto(Long catalogId) {
        Catalog catalog = catalogRepository.findById(catalogId)
                .orElseThrow(() -> new RuntimeException("Catalog not found"));

        return catalog.getChildren().stream()
                .map(item -> new ProductCardDto(
                        item.getId(),
                        item.getArticle(),
                        item.renderName(),
                        item.getDescription(),
                        item.getPurePrice(),
                        item.getDiscount(),
                        item.getTotalPrice(),
                        item.isAvailable(),
                        item.getImageUrl()
                ))
                .toList();
    }

    public ProductCardDto getProductCardById(long id) {
        Item item = findItemById(id);
        return new ProductCardDto(
                item.getId(),
                item.getArticle(),
                item.renderName(),
                item.getDescription(),
                item.getPurePrice(),
                item.getDiscount(),
                item.getTotalPrice(),
                item.isAvailable(),
                item.getImageUrl()
        );
    }

    public List<ProductCardDto> getProductCardsDtoFiltered(Long catalogId, ProductFilter filter, String sortBy, String direction) {
        Catalog catalog = catalogRepository.findById(catalogId)
                .orElseThrow(() -> new RuntimeException("Catalog not found"));

        Sort sort = Sort.unsorted();
        if (sortBy != null && !sortBy.isBlank()) {
            sort = "desc".equalsIgnoreCase(direction) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        }

        // Об'єднуємо динамічний фільтр користувача з прив'язкою до конкретного батьківського каталогу
        Specification<Item> spec = Specification
                .where(ProductSpecifications.withFilter(filter))
                .and((root, query, cb) -> cb.equal(root.get("parent"), catalog));

        return itemRepository.findAll(spec, sort)
                .stream()
                .filter(item -> {
                    // Викликаємо поліморфний метод (для Kit він просумує компоненти,
                    // для Product - застосує знижку)
                    BigDecimal actualPrice = item.getTotalPrice();

                    boolean passMin = filter.minPrice() == null || actualPrice.compareTo(filter.minPrice()) >= 0;
                    boolean passMax = filter.maxPrice() == null || actualPrice.compareTo(filter.maxPrice()) <= 0;

                    return passMin && passMax;
                })
                .map(item -> new ProductCardDto(
                        item.getId(),
                        item.getArticle(),
                        item.renderName(),
                        item.getDescription(),
                        item.getPurePrice(),
                        item.getDiscount(),
                        item.getTotalPrice(),
                        item.isAvailable(),
                        item.getImageUrl()
                ))
                .toList();
    }

    /**
     * Отримати список атрибутів та значень товарів
     * @param id номер товару для пошуку
     * @return словник з парами "атрибут-значення"
     */
    public Map<String, String> getProductDetailsMap(long id) {
        Item item = findItemById(id);
        return extractAttributes(item);
    }

    /**
     * Приводить товар до списку атрибутів та їхніх значень.
     * Враховує також специфічні поля для кожного класу (наприклад, тип інструменту для Tool, вага мотка для Yarn тощо)
     * @param item товар для приведення
     * @return словник
     */
    private Map<String, String> extractAttributes(Item item) {
        Map<String, String> attributes = new LinkedHashMap<>();

        attributes.put("Артикул", item.getArticle());
        attributes.put("Назва", item.renderName());
        attributes.put("Опис", item.getDescription());
        attributes.put("Ціна без знижок", item.getPurePrice().toString());
        attributes.put("Знижка", String.valueOf(item.getDiscount()));
        attributes.put("Загальна ціна", String.valueOf(item.getTotalPrice()));
        attributes.put("В наявності", String.valueOf(item.isAvailable()));
        attributes.put("imageUrl", item.getImageUrl());

        // Якщо це звичайний товар, можемо дістати спільні для Product поля
        if (item instanceof Product product) {
            attributes.put("Бренд", product.getBrand());
            attributes.put("Постачальник", product.getSupplier());
            attributes.put("Країна", product.getCountry());
        }

        // Далі перевіряємо конкретні типи товарів

        if (item instanceof Accessory acc) {
            attributes.put("Тип аксесуару", acc.getAccessoryType());
            attributes.put("Матеріал", acc.getMaterial());
            attributes.put("Розмір", acc.getSize());
            attributes.put("Колір", acc.getColor());
        }
        else if (item instanceof Book book) {
            attributes.put("Назва", book.getName());
            attributes.put("Автор", book.getAuthor());
            attributes.put("Видавництво", book.getPublisher());
            attributes.put("ISBN", book.getIsbn());
            attributes.put("Кількість сторінок", String.valueOf(book.getPages()));
            attributes.put("Рік публікації", String.valueOf(book.getPublicationYear()));
        }
        else if (item instanceof Equipment eq) {
            attributes.put("Тип обладнання", eq.getEquipmentType());
            attributes.put("Потужність", eq.getPowerWatt() + " Вт");
            attributes.put("Кількість операцій", String.valueOf(eq.getOperationsCount()));
            attributes.put("Габарити", eq.getDimensions());
            attributes.put("Вага", eq.getWeightKg() + " кг");
            attributes.put("Гарантія", eq.getWarrantyMonths() + " міс.");
        }
        else if (item instanceof Fabric fabric) {
            attributes.put("Тип тканини", fabric.getFabricType());
            attributes.put("Склад", fabric.getComposition());
            attributes.put("Ширина", fabric.getWidthInCm() + " см");
            attributes.put("Щільність", fabric.getDensity() + " г/м²");
            attributes.put("Колір/Принт", fabric.getColor());
        }
        else if (item instanceof Filler f) {
            attributes.put("Тип", f.getFillerType());
            attributes.put("Щільність, г/м²", String.valueOf(f.getDensity()));
            attributes.put("Гіпоалергенність", (f.isHypoallergenic() ? "Так" : "Ні"));
            attributes.put("Пакування", f.getPackageWeightKg() + " кг");
        }
        else if (item instanceof GiftCertificate cert) {
            attributes.put("Тип", cert.getCertificateType());
            attributes.put("Термін дії", String.valueOf(cert.getValidityMonths()));
            attributes.put("Умови використання", cert.getTermsOfUse());
        }
        // TODO: implement this for KIT
//        else if (item instanceof Kit kit) {
//            attributes.put("Тип", kit.getCertificateType());
//            attributes.put("Термін дії", String.valueOf(kit.getValidityMonths()));
//            attributes.put("Умови використання", kit.getTermsOfUse());
//        }

        else if (item instanceof Pattern pattern) {
            attributes.put("Автор", pattern.getAuthor());
            attributes.put("Рівень складності", pattern.getDifficultyLevel());
            attributes.put("Мова", pattern.getLanguage());
            attributes.put("Формат", pattern.getFormat());
        }
        else if (item instanceof SewingThread thread) {
            attributes.put("Тип нитки", thread.getThreadType());
            attributes.put("Склад", thread.getComposition());
            attributes.put("Товщина/Номер", thread.getThickness());
            attributes.put("Довжина", thread.getLengthInMeters() + " м");
            attributes.put("Колір", thread.getColor());
        }
        else if (item instanceof Tool tool) {
            attributes.put("Тип інструменту", tool.getToolType());
            attributes.put("Матеріал", tool.getMaterial());
            attributes.put("Розмір/діаметр", tool.getSize());
        }
        else if (item instanceof Yarn yarn) {
            attributes.put("Склад", yarn.getFiberContent());
            attributes.put("Довжина нитки", yarn.getLengthInMeters() + " м");
            attributes.put("Вага мотка", yarn.getWeightInGrams() + " г");
            attributes.put("Колір", yarn.getColor());
            attributes.put("Рекомендовані інструменти", yarn.getToolsRecommended());
            attributes.put("Партія (Lot)", yarn.getDyeLot());
        }

        // ... інші типи (Tool, Book, Accessory)
        // TODO: implement mapping for every entity class

        return attributes;
    }


    // ====== Delete methods ======
    public void deleteAll() {
        itemRepository.deleteAll();
    }
}
