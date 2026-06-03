package misha.bondarenko.services;

import misha.bondarenko.services.interfaces.IAssistantService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

@Service
public class AssistantService implements IAssistantService {

    private final ChatClient chatClient;

    public AssistantService(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        this.chatClient = chatClientBuilder
                .defaultSystem("Ти — привітний та професійний ШІ-асистент магазину рукоділля Knit&Sew. " +
                        "Твоя мета — допомагати клієнтам підбирати товари. Завжди використовуй надані тобі інструменти (tools) " +
                        "для пошуку реальних товарів та цін. Ніколи не вигадуй товари, яких немає в базі!")
                .defaultToolNames("getCategoriesTool", "searchProductsTool")
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()) // ПІДКЛЮЧАЄМО ПАМ'ЯТЬ
                .build();
    }

    // Додаємо параметр chatId
    @Override
    public String getResponseFromAi(String chatId, String prompt) {
        return chatClient.prompt(prompt)
                .advisors(a -> a.param(
                        ChatMemory.CONVERSATION_ID,
                        chatId
                ))
                .call()
                .content();
    }
}
