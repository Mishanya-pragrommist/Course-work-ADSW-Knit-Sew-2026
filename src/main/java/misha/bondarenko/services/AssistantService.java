package misha.bondarenko.services;

import misha.bondarenko.services.interfaces.IAssistantService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AssistantService implements IAssistantService {

    private final ChatClient chatClient;

    public AssistantService(ChatClient.Builder chatClientBuilder) {
        // Додаємо назви наших @Bean методів до налаштувань за замовчуванням
        this.chatClient = chatClientBuilder
                .defaultSystem("Ти — привітний та професійний ШІ-асистент магазину рукоділля Knit&Sew. " +
                        "Твоя мета — допомагати клієнтам підбирати товари. Завжди використовуй надані тобі інструменти (tools) " +
                        "для пошуку реальних товарів та цін. Ніколи не вигадуй товари, яких немає в базі!")
                .defaultToolNames("getCategoriesTool", "searchProductsTool") // <-- ОСЬ ТУТ МАГІЯ
                .build();
    }

    @Override
    public String getResponseFromAi(String prompt) {
        return chatClient.prompt(prompt)
                .call()
                .content();
    }
}
