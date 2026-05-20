package misha.bondarenko.records;

/**
 * Інформація про каталог, яка має відображатися на сторінці
 * @param id ідентифікатор в БД (не має показуватися на сторінці)
 * @param name назва каталогу
 * @param imageUrl посилання на зображення
 */
public record CatalogCardDto(
        Long id,
        String name,
        String imageUrl
)
{ }