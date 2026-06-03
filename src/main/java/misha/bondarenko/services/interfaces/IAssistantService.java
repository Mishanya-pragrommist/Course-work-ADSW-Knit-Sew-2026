package misha.bondarenko.services.interfaces;

/**
 * Сервіс для роботи з ШІ-асистентом
 */
public interface IAssistantService {
    String getResponseFromAi(String chatId, String prompt);
}
