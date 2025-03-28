// ---------------------------------------------------------------------------------
// FILE: OpenAIConfig.java
// PACKAGE: com.telusko
// ---------------------------------------------------------------------------------
package com.telusko;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * This is a Spring configuration class that sets up beans (objects managed by Spring)
 * needed for making HTTP requests to the OpenAI API.
 */
@Configuration  // Tells Spring this is a configuration class
public class OpenAIConfig {


    //Creates and configures a RestTemplate bean that will be used to make HTTP requests.

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}