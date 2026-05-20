package misha.bondarenko.services;

import jakarta.persistence.FetchType;
import misha.bondarenko.entities.products.Catalog;
import misha.bondarenko.entities.products.Item;
import misha.bondarenko.records.CatalogCardDto;
import misha.bondarenko.repositories.CatalogRepository;
import misha.bondarenko.repositories.ItemRepository;
import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogService {

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

    @Transactional
    public String getRootCatalogsString() {
        List<Catalog> list = catalogRepository.findAllByParentIsNull();
        return list.stream().map(
                catalog -> catalog.render("") + "\n").collect(Collectors.joining());
    }

    public List<Catalog> getCatalogsByName(String name) {
        return catalogRepository.findByName(name);
    }

//    public List<Item> getProductsOfCatalog(Catalog catalog) {
//        return itemRepository.findByCatalog();
//    }

    // --- Render methods ---

    /**
     * Отримати список об'єктів для подальшого представлення каталогів у вигляді карток
     * @return запис з назвою, описом каталогу та посиланням на зображення
     */
    public List<CatalogCardDto> getCatalogCards() {
        return catalogRepository.findAllByParentIsNull().stream()
                .map(catalog -> new CatalogCardDto(
                        catalog.getId(),
                        catalog.getName(), // Або renderName(), якщо логіка формування назви складна
                        catalog.getImageUrl() // Припускаємо, що це поле вже додано до БД
                ))
                .collect(Collectors.toList());
    }

    /**
     * Отримати об'єкт для подальшого представлення вибраного каталогу
     * @param id номер каталогу для представлення
     * @return запис з назвою, описом каталогом та посиланням на зображення
     */
    public CatalogCardDto getCatalogCardById(Long id) {
        Catalog catalog = catalogRepository.findById(id).orElse(null);
        if (catalog == null) {
            throw new RuntimeException("Catalog not found");
        }
        return new CatalogCardDto(catalog.getId(), catalog.getName(), catalog.getImageUrl());
    }


    // --- Saving methods ---

    public void save(Catalog catalog) {
        catalogRepository.save(catalog);
    }

    public void saveAll(Catalog... catalogs) {
        catalogRepository.saveAll(List.of(catalogs));
    }

}
