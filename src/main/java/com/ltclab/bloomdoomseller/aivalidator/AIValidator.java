package com.ltclab.bloomdoomseller.aivalidator;

import com.ltclab.bloomdoomseller.client.GeminiClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class AIValidator {
    private static final String API_URL = "https://ai-content-detector-ai-gpt.p.rapidapi.com/api/detectText/";
    private static final String API_KEY = "5694d91482msh9bfbc7a06985796p16d240jsnf4261a6cc356";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String JSON_MEDIA_TYPE = "application/json; charset=utf-8";
    private final GeminiClient gemini;

    private static final Logger log = LoggerFactory.getLogger(AIValidator.class);

    @Value("${geminiApiKey}")
    private String key;

    /**
     * Validates if the given subcategory is suitable for the specified category using AI.
     *
     * @param category              The main category.
     * @param subcategory           The subcategory to validate.
     * @param existingSubcategories A set of existing subcategories.
     * @return true if valid; false otherwise.
     * @throws IOException if an error occurs during API request.
     */


}
