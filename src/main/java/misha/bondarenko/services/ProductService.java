package misha.bondarenko.services;

import misha.bondarenko.entities.products.Catalog;
import misha.bondarenko.entities.products.Item;
import misha.bondarenko.records.ProductCardDto;
import misha.bondarenko.repositories.CatalogRepository;
import misha.bondarenko.repositories.ItemRepository;
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
}
