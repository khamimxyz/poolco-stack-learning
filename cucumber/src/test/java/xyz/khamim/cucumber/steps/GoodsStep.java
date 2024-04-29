package xyz.khamim.cucumber.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import xyz.khamim.cucumber.model.Goods;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoodsStep {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;

    @When("^I request GET (.*)$")
    public void iRequestGET(String url) {
        response = restTemplate.getForEntity(url, String.class);
    }

    @Then("^the response status code should be (\\d+)$")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), response.getStatusCode());
    }

    @When("^I send a POST request to the \"([^\"]*)\" endpoint with the following JSON body:$")
    public void iSendPostRequestToEndpointWithJsonBody(String endpoint, String requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        response = restTemplate.exchange(endpoint, HttpMethod.POST, requestEntity, String.class);
    }

    @And("the response should contain the details of the created goods")
    public void theResponseShouldContainTheDetailsOfTheCreatedGoods() throws JsonProcessingException {
        Assertions.assertNotNull(response.getBody(), "Response body should not be null");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseBody = objectMapper.readTree(response.getBody());

        Assertions.assertTrue(responseBody.has("id"), "Response should contain 'id' field");
        Assertions.assertTrue(responseBody.has("name"), "Response should contain 'name' field");
        Assertions.assertTrue(responseBody.has("quantity"), "Response should contain 'quantity' field");
    }

    @And("the goods list should contain {string} with quantity {int}")
    public void theGoodsListShouldContainWithQuantity(String productName, int qty) throws JsonProcessingException {
        Assertions.assertNotNull(response.getBody(), "Response body should not be null");

        ObjectMapper objectMapper = new ObjectMapper();
        Goods goods = objectMapper.readValue(response.getBody(), Goods.class);
        assertEquals(1L, (long) goods.getId());
        assertEquals(productName, goods.getName());
        assertEquals(qty, goods.getQuantity());
    }
}
