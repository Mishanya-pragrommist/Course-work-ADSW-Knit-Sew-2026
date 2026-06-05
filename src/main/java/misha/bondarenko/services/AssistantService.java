package misha.bondarenko.services;

import misha.bondarenko.services.interfaces.IAssistantService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

@Service
public class AssistantService implements IAssistantService {

    private final ChatClient chatClient;
    private final ChatHistoryService historyService;

    public AssistantService(ChatClient.Builder chatClientBuilder,
                            ChatMemory chatMemory,
                            ChatHistoryService historyService) {
        // Детальний системний промпт з правилами поведінки
        // та складання посилань на товари та категорії
        String systemPrompt = """
                Ти — привітний та професійний ШІ-асистент магазину рукоділля Knit&Sew.
                Твоя мета — допомагати клієнтам підбирати товари.
                Завжди використовуй надані тобі інструменти (tools) для пошуку реальних товарів.
                
                ВАЖЛИВІ ПРАВИЛА ФОРМАТУВАННЯ:
                1. Коли ти згадуєш знайдений товар, ЗАВЖДИ роби його назву клікабельним посиланням.
                2. Формат посилання для товару: /Knit_and_Sew/category/{catId}/product/{id} (де {catId} - id каталогу; {id} — це id товару з бази).
                3. Формат посилання для каталогу/категорії: /Knit_and_Sew/category/{id}.
                4. Використовуй списки Markdown для перерахування кількох товарів.
                5. Ніколи не вигадуй товари або посилання, використовуй лише ті ID, які повернули інструменти!
                """;
        this.chatClient = chatClientBuilder
                .defaultSystem(systemPrompt)
                .defaultToolNames("getCategoriesTool", "searchProductsTool")
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()) // ПІДКЛЮЧАЄМО ПАМ'ЯТЬ
                .build();
        this.historyService = historyService;
    }

    // Додаємо параметр chatId
    @Override
    public String getResponseFromAi(
            String chatId,
            String prompt) {

        historyService.addMessage(
                chatId,
                "user",
                prompt
        );

        String response =
                chatClient.prompt(prompt)
                        .advisors(a -> a.param(
                                ChatMemory.CONVERSATION_ID,
                                chatId
                        ))
                        .call()
                        .content();

        historyService.addMessage(
                chatId,
                "assistant",
                response
        );

        return response;
    }
}
