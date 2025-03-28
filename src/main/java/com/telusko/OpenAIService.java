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

/**
 * This service handles the communication with the OpenAI API.
 * It formats requests, sends them to OpenAI, and processes the responses.
 */
@Service
public class OpenAIService {

    // Tool for making HTTP requests
    private final RestTemplate restTemplate;

    // API key for authenticating with OpenAI
    private final String apiKey;

    // Base URL for the OpenAI API
    private final String openaiApiUrl;


    public OpenAIService(
            RestTemplate restTemplate,
            @Value("${openai.api.key}") String apiKey,  // Injects value from application.properties
            @Value("${openai.api.url}") String openaiApiUrl) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.openaiApiUrl = openaiApiUrl;
    }

    /**
     * Sends a request to the OpenAI API to generate a chat completion
     *
     * @param prompt    The text input to send to the AI
     * @param model     The AI model to use (e.g., "gpt-4o")
     * @param maxTokens Maximum length of the generated response
     * @return The AI-generated text response
     */
    public String createChatCompletion(String prompt, String model, int maxTokens) {
        // Set up the HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);  // We're sending JSON
        headers.set("Authorization", "Bearer " + apiKey);  // Add the API key for authentication

        // Create the message object (user's input)
        //This formats your prompt as a user message in the format OpenAI expects.
        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");  // This is a user message
        message.put("content", prompt);  // The actual text input

        // Create the full request body according to OpenAI API specifications
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);  // Which AI model to use
        requestBody.put("messages", List.of(message));  // The conversation history (just one message here)
        requestBody.put("max_tokens", maxTokens);  // Response length limit

        // Combine headers and body into an HTTP entity
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send the POST request to OpenAI API and get the response
        ResponseEntity<Map> response = restTemplate.postForEntity(
                openaiApiUrl + "/chat/completions",  // Complete URL endpoint
                requestEntity,  // Our request with headers and body
                Map.class);  // Expected response type


        // Extract the response content from OpenAI's structured response
        Map responseBody = response.getBody();
// This retrieves the complete JSON response body from OpenAI as a Map object
// The response body contains all the data returned by OpenAI including the message, usage stats, etc.

        List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
// The "choices" field in OpenAI's response contains an array of possible completions
// We're extracting this array and casting it to a List of Maps
// Each Map in this List represents one completion/response from the AI

        Map<String, Object> choice = choices.get(0);  // Get the first (typically only) completion
// We're retrieving the first item (index 0) from the choices List
// In standard usage, there's usually only one response, but OpenAI allows requesting multiple
// This Map contains details about this specific completion like the message and finish reason

        Map<String, String> responseMessage = (Map<String, String>) choice.get("message");
// Each completion contains a "message" field that is itself a Map
// This message Map has fields like "role" (who's speaking - usually "assistant") and "content" (the actual text)
// We're extracting this message Map and casting it to the appropriate type

        // Return just the text content of the AI's response
        return responseMessage.get("content");
    }
}