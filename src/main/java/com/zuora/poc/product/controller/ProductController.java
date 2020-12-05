package com.zuora.poc.product.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuora.model.GETCatalogType;
import com.zuora.model.GETProductType;
import com.zuora.model.ProxyGetProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private AuthTokenController authTokenController;
    @Autowired
    private Environment env;
    private static String EMPTY  = "Empty or Not Applicable";
    private static String NULL  = null;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    GETCatalogType getProducts() {

        String url = env.getProperty("baseURL") + "/v1/catalog/products";

        ObjectMapper mapper = createObjectMapper();
        RestTemplate restTemplate = createRestTemplate(mapper);
        logMessages(mapper, EMPTY, (ProductController.class.getName() + ": Request message"));

        ParameterizedTypeReference<GETCatalogType> returnType = new ParameterizedTypeReference<GETCatalogType>() {};
        GETCatalogType getCatalogType = restTemplate.exchange(url, HttpMethod.GET,
                createOrderRequestHeaders(NULL), returnType).getBody();
        logMessages(mapper, getCatalogType, (ProductController.class.getName() + ":Response message"));
        return getCatalogType;
    }

    @GetMapping("/object/{id}")
    @ResponseStatus(HttpStatus.OK)
    ProxyGetProduct getObjectProductById(@PathVariable("id") String id) {

        String url = env.getProperty("baseURL") + "/v1/object/product/"+id;
        ObjectMapper mapper = createObjectMapper();
        RestTemplate restTemplate = createRestTemplate(mapper);
        logMessages(mapper, id, (ProductController.class.getName() + ": Request message"));

        ProxyGetProduct responseEntity = restTemplate.exchange(url, HttpMethod.GET,
                createOrderRequestHeaders(NULL), ProxyGetProduct.class).getBody();
        logMessages(mapper, responseEntity, (ProxyGetProduct.class.getName() + ":Response   message"));
        return responseEntity;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    GETProductType getProductById(@PathVariable("id") String id) {

        String url = env.getProperty("baseURL") + "/v1/catalog/product/"+id;
        ObjectMapper mapper = createObjectMapper();
        RestTemplate restTemplate = createRestTemplate(mapper);
        logMessages(mapper, id, (ProductController.class.getName() + ": Request message"));

        GETProductType responseEntity = restTemplate.exchange(url, HttpMethod.GET,
                createOrderRequestHeaders(NULL), GETProductType.class).getBody();
        logMessages(mapper, responseEntity, (ProxyGetProduct.class.getName() + ":Response   message"));
        return responseEntity;
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, false);

        return mapper;
    }
    private RestTemplate createRestTemplate(ObjectMapper mapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(mapper);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, converter );
        return restTemplate;
    }
    private <T> HttpEntity<?> createOrderRequestHeaders(@Nullable T objBody) {
        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + authTokenController.getBearerToken());

        final String[] accepts = {
                "application/json"
        };
        List<MediaType> mediaTypes = MediaType.parseMediaTypes(StringUtils.arrayToCommaDelimitedString(accepts));
        headers.setAccept(mediaTypes);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("zuora-version", "257.0");

        // create request
        HttpEntity<?> requestHeaders;
        if(objBody == null) {
            requestHeaders = new HttpEntity<String>(null, headers);
        }
        else {
            requestHeaders = new HttpEntity<>(objBody, headers);
        }
        return  requestHeaders;
    }

    private void logMessages(ObjectMapper mapper, Object objClass, String message) {
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

    /*@GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    GETCatalogType getProducts_tryouts() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, false);
//        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        //logMessages(mapper, catalog, (GETCatalogType.class.getName() + ": Request message"));
        String url = env.getProperty("baseURL") + "/v1/catalog/products";
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(mapper);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, converter );

        GETCatalogType responseEntity = null;

        //new ParameterizedTypeReference<ArrayList<GETProductType>>(){} : Cannot deserialize instance of `java.util.ArrayList<com.zuora.model.GETProductType>` out of START_OBJECT token
        //GETProductType[].class : Cannot deserialize instance of `[Lcom.zuora.model.GETProductType;` out of START_OBJECT token
        // : Cannot deserialize instance of `java.lang.String` out of START_ARRAY token
        // GETCatalogType.class : at [Source: (PushbackInputStream); line: 15, column: 26] (through reference chain: com.zuora.model.GETCatalogType["products"]->java.util.ArrayList[0]->com.zuora.model.GETProductType["productRatePlans"])
        *//*ResponseEntity<Object> resEntity1 = restTemplate.exchange(url, HttpMethod.GET,
                createOrderRequestHeaders(), Object.class);
        //logMessages(mapper, resEntity1, "Response= ");

        logMessages(mapper, resEntity1.getBody(), "Response Body= ");
        try {
            mapper.readValue(resEntity1.getBody().toString(), GETProductType.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }*//*
        ParameterizedTypeReference<Map<String, Object>> returnMapType = new ParameterizedTypeReference<Map<String, Object>>() {};
        ResponseEntity<Map<String, Object>> resMapEntity = restTemplate.exchange(url, HttpMethod.GET,
                createOrderRequestHeaders(), returnMapType);

        Map<String, Object> map = (Map<String, Object>)resMapEntity.getBody();
        for (String key : resMapEntity.getBody().keySet()) {
            System.out.println("Key=" + key);
            if(key.equals("products")) {
                System.out.println("Json String= " + resMapEntity.getBody().get(key).toString());
                ArrayList<Map> arrayList = ((ArrayList)resMapEntity.getBody().get(key));
                for (Map mp : arrayList) {
                    try {
//                        System.out.println("" + JSON.toJSONString(map));
                        mapper.convertValue(mp, GETProductType.class);
                        //mapper.readValue(resMapEntity.getBody().get(key).toString(),GETProductType[].class );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        ParameterizedTypeReference<GETCatalogType> returnType = new ParameterizedTypeReference<GETCatalogType>() {};
        ResponseEntity<GETCatalogType> resEntity = restTemplate.exchange(url, HttpMethod.GET,
                createOrderRequestHeaders(), returnType);
        responseEntity = resEntity.getBody();
        //logMessages(mapper, responseEntity, (GETProductType.class.getName() + ":Response message"));
        return responseEntity;
    }*/
}
