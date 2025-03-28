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

    /**
     * Endpoint that handles chat completion requests
     *
     * @param request The incoming request containing prompt, model, and token settings
     * @return A ResponseEntity containing the AI-generated response
     */
    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chatCompletion(@RequestBody ChatRequest request) {
        // Call the service to get a response from OpenAI
        String response = openAIService.createChatCompletion(
                request.getPrompt(),
                // Use the provided model
                request.getModel(),
                // Use the provided max tokens or default to 500 if not valid
                request.getMaxTokens() > 0 ? request.getMaxTokens() : 500);

        // Return a successful HTTP response with the AI-generated content
        return ResponseEntity.ok(new ChatResponse(response));
    }
}
