package misha.bondarenko.services;

import misha.bondarenko.entities.products.Catalog;
import misha.bondarenko.entities.products.Item;
import misha.bondarenko.entities.products.Kit;
import misha.bondarenko.entities.products.Product;
import misha.bondarenko.records.ProductCardDto;
import misha.bondarenko.repositories.CatalogRepository;
import misha.bondarenko.repositories.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public String renderProducts() {
        List<Item> items = itemRepository.findAll();
        StringBuilder sb = new StringBuilder();
        for (Item item : items) {
            if (item instanceof Product || item instanceof Kit) {
                sb.append(item.render("")).append("\n");
            }
        }
        return sb.toString();
    }

    public List<ProductCardDto> getProductCards(Long catalogId) {
        Catalog catalog = catalogRepository.findById(catalogId)
                .orElseThrow(() -> new RuntimeException("Catalog not found"));

        return catalog.getChildren().stream()
                .filter(item -> item instanceof Product || item instanceof Kit)
                .map(item -> new ProductCardDto(
                        item.getId(),
                        item.getName(),
                        ((Product) item).getPurePrice(),
                        ((Product)item).getDiscount(),
                        ((Product) item).getPrice(),
                        ((Product)item).isAvailable(),
                        item.getImageUrl()
                ))
                .toList();
    }
}
