package misha.bondarenko.services;

import misha.bondarenko.entities.products.Category;
import misha.bondarenko.records.dto.CategoryCardDto;
import misha.bondarenko.repositories.CategoryRepository;
import misha.bondarenko.services.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // --- Render methods ---

    /**
     * Отримати список об'єктів для подальшого представлення каталогів у вигляді карток
     * @return запис з назвою, описом каталогу та посиланням на зображення
     */
    public List<CategoryCardDto> getCategoryCards() {
        return categoryRepository.findAll().stream()
                .map(catalog -> new CategoryCardDto(
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
    public CategoryCardDto getCategoryCardById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        return new CategoryCardDto(category.getId(), category.getName(), category.getImageUrl());
    }


    // ====== Saving methods ======

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public void saveAll(Category... categories) {
        categoryRepository.saveAll(List.of(categories));
    }


    // ====== Deleting methods ======

    public void deleteAll() {
        categoryRepository.deleteAll();
    }
}
