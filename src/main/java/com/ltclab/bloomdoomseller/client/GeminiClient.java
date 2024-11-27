package com.ltclab.bloomdoomseller.client;

import com.ltclab.bloomdoomseller.dto.request.ai.Content;
import com.ltclab.bloomdoomseller.dto.request.ai.GeminiRequestDTO;
import com.ltclab.bloomdoomseller.dto.request.ai.Part;
import com.ltclab.bloomdoomseller.dto.response.ai.GeminiResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@FeignClient(name = "Gemini", url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent")
public interface GeminiClient {

    @PostMapping
    GeminiResponseDTO getMessageFeign(@RequestParam("key") String apiKey, @RequestBody GeminiRequestDTO request);

    // Method for validating the subcategory using AI
    default boolean validateSubcategory(String category, String subcategory, String apiKey) throws IOException {
        String prompt = createPrompt(category, subcategory);

        GeminiRequestDTO request = buildRequest(prompt);

        // Send the request to the Gemini AI and get the response
        GeminiResponseDTO responseDto = getMessageFeign(apiKey, request);
        String response = responseDto.getResponse();

        return response.trim().equalsIgnoreCase("Yes");
    }

    default boolean validateType(String category, String subcategory, String type, String apiKey) throws IOException {
        String prompt = createPromptType(category, subcategory, type);

        GeminiRequestDTO request = buildRequest(prompt);

        // Send the request to the Gemini AI and get the response
        GeminiResponseDTO responseDto = getMessageFeign(apiKey, request);
        String response = responseDto.getResponse();

        return response.trim().equalsIgnoreCase("Yes");
    }

    // Helper method to create a prompt
    private String createPrompt(String category, String subcategory) {
        return """
                Evaluate whether the term '%s' can be classified as a valid subcategory under the category '%s'.
                Consider both the meaning of the words and their relevance to each other.
                Respond with 'Yes' if it is a valid subcategory, or 'No' if it is not.
                """.formatted(subcategory, category);
    }

    private String createPromptType(String category, String subcategory, String type) {
        return """
            You are tasked with evaluating the term '%s' to determine if it can be considered a valid type for the subcategory '%s' within the category '%s'. 
            Please consider both semantic relevance and common usage in the context of the subcategory and category. Focus on industry standards, scientific classifications, or typical categorizations if applicable.
            Provide your answer in one word: 'Yes' if the type is appropriate and valid, or 'No' if it is not.
            """.formatted(type, subcategory, category);
    }


    // Helper method to build the request for AI
    private GeminiRequestDTO buildRequest(String prompt) {
        Part part = new Part();
        part.setText(prompt);

        Content content = new Content();
        content.setParts(new ArrayList<>(Arrays.asList(part)));

        GeminiRequestDTO requestDTO = new GeminiRequestDTO();
        requestDTO.setContents(new ArrayList<>(Arrays.asList(content)));

        return requestDTO;
    }
}
