package misha.bondarenko.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

public class AiService {

    private final ChatClient chatClient;

    public AiService(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    public String getResponseFromAi(String prompt) {
        return chatClient.prompt(prompt)
                .call()
                .content();
    }
}
