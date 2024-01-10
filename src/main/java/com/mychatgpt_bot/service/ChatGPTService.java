package com.mychatgpt_bot.service;

import com.mychatgpt_bot.config.OpenAIProperties;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.net.URI;

@Component
public class ChatGPTService {

    public static final String RESPONSE_TO_MEDIA_CONTENT = "As a text-based AI language model, my capabilities are limited to creating and understanding text. I do not have direct access to or understanding of other types of media such as images, audio or video. However, I can help you create text descriptions or provide information related to different types of media based on the text information you provide.";

    public final Logger logger = LoggerFactory.getLogger(ChatGPTService.class);
    final OpenAIProperties openAIProperties;

    public ChatGPTService(OpenAIProperties openAIProperties) {
        this.openAIProperties = openAIProperties;
    }

    public String askChatGPTText(String msg) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = setHeaders();

        JSONObject request = new JSONObject();
        request.put("model", openAIProperties.getChatGptModel());
        request.put("messages", new JSONArray().put(new JSONObject().put("role", "system").put("content", "You are a helpful assistant.")));
        request.getJSONArray("messages").put(new JSONObject().put("role", "user").put("content", msg));

        HttpEntity<String> requestEntity = new HttpEntity<String>(request.toString(), headers);

        URI chatGptUrl = URI.create(openAIProperties.getOpenaiUrlCompletions());

        logger.info("Post request to OpenAI API");
        System.out.println(requestEntity);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(chatGptUrl, requestEntity, String.class);
        logger.info("Success. Received response from OpenAI API");

        JSONObject responseJson = new JSONObject(responseEntity.getBody());
        JSONArray choices = responseJson.getJSONArray("choices");

        JSONObject firstChoice = choices.getJSONObject(0);
        return firstChoice.getJSONObject("message").getString("content");
    }

    private HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAIProperties.getApiKey());
        return headers;
    }
}
