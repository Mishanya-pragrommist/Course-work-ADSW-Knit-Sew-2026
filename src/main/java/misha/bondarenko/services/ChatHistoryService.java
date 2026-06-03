package misha.bondarenko.services;

import misha.bondarenko.records.dto.ChatMessageDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Сервіс для отримання історії переписки з ШІ-асистентом
 */
@Service
public class ChatHistoryService {

    private final Map<String, List<ChatMessageDto>> history =
            new ConcurrentHashMap<>();

    public void addMessage(String chatId,
                           String role,
                           String content) {

        history.computeIfAbsent(
                chatId,
                id -> new ArrayList<>()
        ).add(new ChatMessageDto(role, content));
    }

    public List<ChatMessageDto> getHistory(String chatId) {

        return history.getOrDefault(
                chatId,
                Collections.emptyList()
        );
    }
}
