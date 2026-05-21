package misha.bondarenko.services;

import misha.bondarenko.entities.products.Catalog;
import misha.bondarenko.entities.products.Item;
import misha.bondarenko.entities.products.Product;
import misha.bondarenko.records.dto.ProductCardDto;
import misha.bondarenko.records.filters.ProductFilter;
import misha.bondarenko.repositories.CatalogRepository;
import misha.bondarenko.repositories.ItemRepository;
import misha.bondarenko.specifications.ProductSpecifications;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final CatalogRepository catalogRepository;
    private final ItemRepository itemRepository;

    public ProductService(ItemRepository itemRepository,
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
                        item.getName(),
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
                item.getName(),
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
                .map(item -> new ProductCardDto(
                        item.getId(),
                        item.getArticle(),
                        item.getName(),
                        item.getDescription(),
                        item.getPurePrice(),
                        item.getDiscount(),
                        item.getTotalPrice(),
                        item.isAvailable(),
                        item.getImageUrl()
                ))
                .toList();
    }
}
