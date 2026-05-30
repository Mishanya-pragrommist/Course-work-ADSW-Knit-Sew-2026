package misha.bondarenko.services.interfaces;

import misha.bondarenko.entities.products.*;
import misha.bondarenko.records.dto.KitComponentCardDto;
import misha.bondarenko.records.dto.ProductCardDto;
import misha.bondarenko.records.filters.ProductFilter;
import misha.bondarenko.specifications.ProductSpecifications;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface IItemService {
    public Item findItemById(Long id);

    public Item findItemByName(String name);

    public List<ProductCardDto> getProductCardsDto(Long catalogId);

    ProductCardDto getProductCardById(Long id);

    List<ProductCardDto> getProductCardsDtoFiltered(Long catalogId,
                                                           ProductFilter filter,
                                                           String sortBy,
                                                           String direction);
    /**
     * Отримати список атрибутів та значень товарів
     * @param id номер товару для пошуку
     * @return словник з парами "атрибут-значення"
     */
    Map<String, String> getProductDetailsMap(Long id);

    // ====== Delete methods ======
    void deleteAll();
}
