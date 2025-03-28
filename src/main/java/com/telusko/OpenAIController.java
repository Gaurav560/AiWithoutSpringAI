package com.telusko;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class OpenAIController {

    private final OpenAIService openAIService;

    public OpenAIController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chatCompletion(@RequestBody ChatRequest request) {
        String response = openAIService.createChatCompletion(
                request.getPrompt(),
                request.getModel() != null ? request.getModel() : "gpt-3.5-turbo",
                request.getMaxTokens() > 0 ? request.getMaxTokens() : 500);

        return ResponseEntity.ok(new ChatResponse(response));
    }
}



