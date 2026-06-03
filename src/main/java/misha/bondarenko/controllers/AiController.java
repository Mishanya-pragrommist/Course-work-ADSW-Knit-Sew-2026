package misha.bondarenko.controllers;

import jakarta.servlet.http.HttpSession;
import misha.bondarenko.services.interfaces.IAssistantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final IAssistantService assistantService;

    public AiController(IAssistantService assistantService) {
        this.assistantService = assistantService;
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

}
