package misha.bondarenko.services;

import misha.bondarenko.entities.products.*;
import misha.bondarenko.records.dto.KitComponentCardDto;
import misha.bondarenko.records.dto.ProductCardDto;
import misha.bondarenko.records.filters.ProductFilter;
import misha.bondarenko.repositories.CategoryRepository;
import misha.bondarenko.repositories.ItemRepository;
import misha.bondarenko.repositories.KitRepository;
import misha.bondarenko.services.interfaces.IItemService;
import misha.bondarenko.specifications.ProductSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class ItemService implements IItemService {
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final KitRepository kitRepository;

    public ItemService(ItemRepository itemRepository,
                       CategoryRepository categoryRepository,
                       KitRepository kitRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.kitRepository = kitRepository;
    }

    public Item findItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public Item findItemByName(String name) {
        return itemRepository.findByName(name).orElse(null);
    }

    /**
     * Отримати список карток товарів з певної категорії для відображення сітки товарів
     * @param catalogId номер категорії для пошуку
     * @return повний список карток товарів
     */
    public List<ProductCardDto> getProductCardsDto(Long catalogId) {
        Category category = categoryRepository.findById(catalogId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return category.getChildren().stream()
                .map(this::adaptProductToCardDto)
                .toList();
    }

    /**
     * Отримати картку вибраного товару
     * @param id номер шуканого товару
     * @return картка товару
     */
    public ProductCardDto getProductCardById(Long id) {
        return adaptProductToCardDto(findItemById(id));
    }

    /**
     * Конвертувати товар до виду картки товару для передачі на сторінку
     * @param item товар
     * @return картка товару
     */
    private ProductCardDto adaptProductToCardDto(Item item) {
        return new ProductCardDto(
                item.getId(),
                item.getArticle(),
                item.renderName(),
                item.getDescription(),
                item.getBasePrice(), // Ціна без знижок
                item.getDiscount(),
                item.getTotalPrice(), // Ціна зі знижкою
                item.getStockQuantity(),
                item.isAvailable(), // Залежить від прапорця доступності та від к-сті залишків на складі
                item.getImageUrl(),
                item instanceof Kit kit ? kit.getComponents().stream()
                        .map(elem -> new KitComponentCardDto(this.adaptProductToCardDto(elem.getItem()), elem.getQuantity()))
                        .toList() : null
        );
    }

    /**
     * Відфільтрувати та сортувати картки товарів з вибраного каталогу
     * @param catalogId номер каталогу для пошуку товарів
     * @param filter фільтр (враховує загальні та специфічні атрибути)
     * @param sortBy критерій сортування (за ціною, за новизною тощо)
     * @param direction напрямок сортування (від найменшого до найбільшого та навпаки)
     * @return відфільтрований та відсортований список карток товарів
     */
    public List<ProductCardDto> getProductCardsDtoFiltered(Long catalogId,
                                                           ProductFilter filter,
                                                           String sortBy,
                                                           String direction) {
        Category category = categoryRepository.findById(catalogId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Specification<Item> spec = Specification
                .where(ProductSpecifications.withFilter(filter))
                .and((root, query, cb) -> cb.equal(root.get("parent"), category));

        Stream<ProductCardDto> stream = itemRepository.findAll(spec)
                .stream()
                .filter(item -> {
                    // Викликаємо поліморфний метод
                    // (для Kit він просумує компоненти,
                    // для Product - застосує знижку)
                    BigDecimal actualPrice = item.getTotalPrice();

                    boolean passMin = filter.minPrice() == null || actualPrice.compareTo(filter.minPrice()) >= 0;
                    boolean passMax = filter.maxPrice() == null || actualPrice.compareTo(filter.maxPrice()) <= 0;

                    return passMin && passMax;
                })
                .map(this::adaptProductToCardDto);

        // Динамічне сортування (може бути розширене в майбутньому)
        assert sortBy != null;
        if (sortBy.equals("totalPrice")) {
            // За загальною ціною (базова ціна та знижка)
            Comparator<ProductCardDto> priceComparator = Comparator.comparing(ProductCardDto::newPrice);
            if (direction.equalsIgnoreCase("desc")) {
                priceComparator = priceComparator.reversed();
            }
            stream = stream.sorted(priceComparator);
        }

        return stream.toList();
    }

    /**
     * Отримати список атрибутів та значень товарів
     * @param id номер товару для пошуку
     * @return словник з парами "атрибут-значення"
     */
    public Map<String, String> getProductDetailsMap(Long id) {
        Item item = findItemById(id);
        return extractAttributes(item);
    }

    /**
     * Приводить товар до списку атрибутів та їхніх значень.
     * Враховує також специфічні поля для кожного класу
     * (наприклад, тип інструменту для Tool, вага мотка для Yarn тощо)
     * @param item товар для приведення
     * @return словник
     */
    private Map<String, String> extractAttributes(Item item) {
        Map<String, String> attributes = new LinkedHashMap<>();

        attributes.put("Артикул", item.getArticle());
        attributes.put("Кількість на складі",  String.valueOf(item.getStockQuantity()));

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
            attributes.put("Термін дії", cert.getValidityMonths() + " місяців");
            attributes.put("Умови використання", cert.getTermsOfUse());
        }
        else if (item instanceof Kit kit) {
            attributes.put("Кількість компонентів", String.valueOf(kit.getComponents().size()));
        }

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

        return attributes;
    }


    // ====== Delete methods ======
    public void deleteAll() {
        kitRepository.deleteAll();
        itemRepository.deleteAll();
    }
}
