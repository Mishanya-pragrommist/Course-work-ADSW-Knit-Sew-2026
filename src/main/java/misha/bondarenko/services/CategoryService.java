package misha.bondarenko.services;

import misha.bondarenko.entities.products.Catalog;
import misha.bondarenko.entities.products.Item;
import misha.bondarenko.repositories.CatalogRepository;
import misha.bondarenko.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CatalogRepository catalogRepository;
    private ItemRepository itemRepository;

    @Autowired
    public void setCatalogRepository(CatalogRepository catalogRepository, ItemRepository itemRepository) {
        this.catalogRepository = catalogRepository;
        this.itemRepository = itemRepository;
    }

    // Maybe this method won't be useful
    public List<Catalog> getAllCatalogs() {
        return catalogRepository.findAll();
    }


    public Catalog getCatalogById(Long id) {
        return catalogRepository.findById(id).orElse(null);
    }

    /**
     * Отримати усі кореневі каталоги типу "пряжа", "інструменти", "аксесуари" тощо
     * @return список кореневих каталогів
     */
    public List<Catalog> getRootCatalogs() {
        return catalogRepository.findAllByParentIsNull();
    }

    public List<Catalog> getCatalogsByName(String name) {
        return catalogRepository.findByName(name);
    }

//    public List<Item> getProductsOfCatalog(Catalog catalog) {
//        return itemRepository.findByCatalog();
//    }

    // --- Saving methods ---

    public void save(Catalog catalog) {
        catalogRepository.save(catalog);
    }

    // 
}
