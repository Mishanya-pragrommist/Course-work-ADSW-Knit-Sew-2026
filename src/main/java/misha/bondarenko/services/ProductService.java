package misha.bondarenko.services;

import misha.bondarenko.entities.products.Catalog;
import misha.bondarenko.entities.products.Item;
import misha.bondarenko.entities.products.Kit;
import misha.bondarenko.entities.products.Product;
import misha.bondarenko.records.ProductCardDto;
import misha.bondarenko.repositories.CatalogRepository;
import misha.bondarenko.repositories.ItemRepository;
import misha.bondarenko.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final CatalogRepository catalogRepository;
    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;

    public ProductService(ItemRepository itemRepository,
                          CatalogRepository catalogRepository,
                          ProductRepository productRepository) {
        this.itemRepository = itemRepository;
        this.catalogRepository = catalogRepository;
        this.productRepository = productRepository;
    }

    public Item findItemByName(String name) {
        return itemRepository.findByName(name).orElse(null);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Transactional
    public String renderItems() {
        List<Item> items = itemRepository.findAll();
        StringBuilder sb = new StringBuilder();
        for (Item item : items) {
            sb.append(item.render("")).append("\n");
        }
        return sb.toString();
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
                .filter(item -> item instanceof Product)
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
