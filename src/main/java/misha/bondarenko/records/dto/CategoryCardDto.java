package misha.bondarenko.records.dto;

/**
 * Інформація про каталог, яка має відображатися на сторінці
 * @param id ідентифікатор в БД (не має показуватися на сторінці)
 * @param name назва каталогу
 * @param imageUrl посилання на зображення в папці /static/images/
 */
public record CategoryCardDto(
        Long id,
        String name,
        String imageUrl
)
{ }