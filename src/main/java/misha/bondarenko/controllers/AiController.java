package misha.bondarenko.controllers;

import jakarta.servlet.http.HttpSession;
import misha.bondarenko.records.dto.ChatMessageDto;
import misha.bondarenko.services.ChatHistoryService;
import misha.bondarenko.services.interfaces.IAssistantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final IAssistantService assistantService;
    private final ChatHistoryService historyService;

    public AiController(IAssistantService assistantService,
                        ChatHistoryService historyService) {
        this.assistantService = assistantService;
        this.historyService = historyService;
    }

    /**
     * Отримати відповідь від розумного асистента на основі запиту
     */
    @GetMapping("/ask")
    public ResponseEntity<String> getResponseFromAi(
            HttpSession session,
            @RequestParam String prompt) {

        String chatId = session.getId();

        return ResponseEntity.ok(
                assistantService.getResponseFromAi(chatId, prompt)
        );
    }

    @GetMapping("/history")
    public ResponseEntity<List<ChatMessageDto>> getHistory(
            HttpSession session) {

        return ResponseEntity.ok(
                historyService.getHistory(
                        session.getId()
                )
        );
    }
}
