package com.telusko;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String openaiApiUrl;

    public OpenAIService(
            RestTemplate restTemplate,
            @Value("${openai.api.key}") String apiKey,
            @Value("${openai.api.url}") String openaiApiUrl) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.openaiApiUrl = openaiApiUrl;
    }

    /**
     * Send a completion request to OpenAI API
     *
     * @param prompt The prompt to complete
     * @param model The model to use (e.g., "gpt-4o")
     * @param maxTokens Maximum number of tokens in the response
     * @return The completion response
     */
    public String createChatCompletion(String prompt, String model, int maxTokens) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", List.of(message));
        requestBody.put("max_tokens", maxTokens);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                openaiApiUrl + "/chat/completions",
                requestEntity,
                Map.class);

        // Extract the response content
        Map responseBody = response.getBody();
        List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
        Map<String, Object> choice = choices.get(0);
        Map<String, String> responseMessage = (Map<String, String>) choice.get("message");

        return responseMessage.get("content");
    }
}