package xyz.khamim.slash.ecommerce.cucumber.util;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;

public class RestUtil {

    private static final String BASE_URL = "http://localhost:9009/api";

    public static <T, U> U post(TestRestTemplate restTemplate, String api, T payload, Class<U> uClass) {

        return post(restTemplate, api, payload, uClass, null);
    }

    public static <T, U> U post(
            TestRestTemplate restTemplate, String api, T payload,
            Class<U> uClass, String token) {

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if(token != null) {
           headers.setBearerAuth(token);
        }

        final String requestBody = JSONUtil.convertToJson(payload);
        final HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        final String endpoint = BASE_URL + api;
        final ResponseEntity<String> responseEntity = restTemplate.exchange(
                endpoint, HttpMethod.POST, requestEntity, String.class);

        return JSONUtil.convertToObject(responseEntity.getBody(), uClass);
    }

    public static <T, U> U get(TestRestTemplate restTemplate, String api, Class<U> uClass) {

        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        final HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        final String endpoint = BASE_URL + api;
        final ResponseEntity<String> responseEntity = restTemplate.exchange(
                endpoint, HttpMethod.GET, requestEntity, String.class);

        return JSONUtil.convertToObject(responseEntity.getBody(), uClass);
    }
}
