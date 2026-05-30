package misha.bondarenko.enums;

/**
 * Статус замовлення
 */
public enum OrderStatus {
    // «Нове», «Зібрано», «Доставляється», «Доставлено», «Оплачено», «Скасовано»
    NEW("Нове"),
    PACKED("Зібрано"),
    DELIVERING("Доставляється"),
    DELIVERED("Доставлено"),
    PAID("Оплачено"),
    CANCELED("Скасовано");

    final String title;
    OrderStatus(String title) {
        this.title = title;
    }
}
