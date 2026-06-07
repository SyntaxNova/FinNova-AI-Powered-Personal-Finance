package com.atharva.finance_tracker.ai;

import com.atharva.finance_tracker.dto.gemini.Content;
import com.atharva.finance_tracker.dto.gemini.GeminiRequest;
import com.atharva.finance_tracker.dto.gemini.GeminiResponse;
import com.atharva.finance_tracker.dto.gemini.Part;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public GeminiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateInsights(String prompt) {

        String url =
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key="
                        + apiKey;

        Part part = new Part(prompt);

        Content content = new Content(List.of(part));

        GeminiRequest request =
                new GeminiRequest(List.of(content));

//        System.out.println("API KEY = " + apiKey);
//        System.out.println("URL = " + url);

        try {

            GeminiResponse response =
                    restTemplate.postForObject(
                            url,
                            request,
                            GeminiResponse.class
                    );

            if (response == null
                    || response.getCandidates() == null
                    || response.getCandidates().isEmpty()) {

                return "No response received from Gemini";
            }

            return response
                    .getCandidates()
                    .get(0)
                    .getContent()
                    .getParts()
                    .get(0)
                    .getText();

        } catch (Exception e) {

            e.printStackTrace();

            return "Error: " + e.getMessage();
        }
    }
}