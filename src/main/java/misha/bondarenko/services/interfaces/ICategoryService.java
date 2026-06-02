package misha.bondarenko.services.interfaces;

import misha.bondarenko.entities.products.Category;
import misha.bondarenko.records.dto.CategoryCardDto;

import java.util.List;

public interface ICategoryService {
    // --- Render methods ---

    /**
     * Отримати список об'єктів для подальшого представлення каталогів у вигляді карток
     * @return запис з назвою, описом каталогу та посиланням на зображення
     */
    List<CategoryCardDto> getCategoryCards();

    /**
     * Отримати об'єкт для подальшого представлення вибраного каталогу
     * @param id номер каталогу для представлення
     * @return запис з назвою, описом каталогом та посиланням на зображення
     */
    CategoryCardDto getCategoryCardById(Long id);

    // ====== Saving methods ======

    void save(Category category);
    void saveAll(Category... categories);


    // ====== Deleting methods ======
    public void deleteAll();
}
