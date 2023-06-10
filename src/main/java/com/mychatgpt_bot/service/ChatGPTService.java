package com.mychatgpt_bot.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.net.URI;

public class ChatGPTService {
    public static final String RESPONSE_TO_MEDIA_CONTENT = "As a text-based AI language model, my capabilities are limited to creating and understanding text. I do not have direct access to or understanding of other types of media such as images, audio or video. However, I can help you create text descriptions or provide information related to different types of media based on the text information you provide.";

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.url.completions}")
    private String urlCompletions;

    @Value("${chatGpt.model}")
    private String model;

    public String askChatGPTText(String msg) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = setHeaders();

        JSONObject request = new JSONObject();
        request.put("model", model);
        request.put("messages", new JSONArray().put(new JSONObject().put("role", "system").put("content", "You are a helpful assistant.")));
        request.getJSONArray("messages").put(new JSONObject().put("role", "user").put("content", msg));

        HttpEntity<String> requestEntity = new HttpEntity<String>(request.toString(), headers);

        URI chatGptUrl = URI.create(urlCompletions);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(chatGptUrl, requestEntity, String.class);

        JSONObject responseJson = new JSONObject(responseEntity.getBody());
        JSONArray choices = responseJson.getJSONArray("choices");

        JSONObject firstChoice = choices.getJSONObject(0);
        return firstChoice.getJSONObject("message").getString("content");
    }

    private HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        return headers;
    }
}
