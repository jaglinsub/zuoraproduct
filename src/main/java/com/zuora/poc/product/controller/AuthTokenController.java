package com.zuora.poc.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthTokenController {
    @Autowired
    private Environment env;
    public String getBearerToken() {

        String url = env.getProperty("baseURL") + "/oauth/token";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
        parametersMap.add("client_id", env.getProperty("client_id"));
        parametersMap.add("grant_type", env.getProperty("grant_type"));
        parametersMap.add("client_secret", env.getProperty("client_secret"));

        //request entity is created with request headers
        HttpEntity requestEntity = new HttpEntity(parametersMap, requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        String strResponseEntity = "EMPTY";

        ResponseEntity<String> responseEntity = restTemplate.exchange(url,
                HttpMethod.POST,
                requestEntity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(responseEntity.getBody());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        JsonNode access_token = root.path("access_token");
        logMessages(mapper, access_token.asText(), "responseEntity Bearer Token: ");
        strResponseEntity = access_token.asText() != null ? access_token.asText() : "EMPTY";
        return strResponseEntity;
    }

    private <T> void logMessages(ObjectMapper mapper, Object objClass, String message) {
        String jsonString = null;
        if(mapper == null) {
            mapper = new ObjectMapper();
        }
        try {
            jsonString = mapper.writeValueAsString(objClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(message);
        System.out.println(jsonString);
    }
}
