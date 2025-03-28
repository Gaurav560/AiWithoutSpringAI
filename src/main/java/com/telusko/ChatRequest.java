package com.telusko;

/**
 * This class represents the structure of the request that will be sent to the API endpoint.
 * It contains all the parameters needed to make a chat completion request.
 */
public class ChatRequest {
    // The text input (question/instruction) that will be sent to the AI model
    private String prompt;

    // The AI model to be used (e.g., "gpt-3.5-turbo", "gpt-4o")
    private String model;

    // The maximum number of tokens (words/parts of words) the AI should generate in its response
    private int maxTokens;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }


    public String getModel() {
        return model;
    }


    public void setModel(String model) {
        this.model = model;
    }


    public int getMaxTokens() {
        return maxTokens;
    }


    public void setMaxTokens(int maxTokens) {
        this.maxTokens = maxTokens;
    }
}
