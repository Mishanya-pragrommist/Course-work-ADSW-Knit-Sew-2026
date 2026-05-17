package misha.bondarenko.enums;

/**
 * Статус замовлення
 */
public enum OrderStatus {
    NEW("Нове"),
    PROCESSING("В обробці"),
    DELIVERED("Доставлено"),
    FINISHED("Завершено"),
    RETURNED("Повернуто");

    final String title;
    OrderStatus(String title) {
        this.title = title;
    }
}
