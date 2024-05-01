package xyz.khamim.slash.ecommerce.cucumber.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import xyz.khamim.slash.ecommerce.cucumber.Constant;
import xyz.khamim.slash.ecommerce.cucumber.payload.IdResponse;
import xyz.khamim.slash.ecommerce.cucumber.payload.PromotionResponse;
import xyz.khamim.slash.ecommerce.cucumber.util.DataPreparationUtil;
import xyz.khamim.slash.ecommerce.cucumber.util.RestUtil;
import xyz.khamim.slash.ecommerce.payload.ProductReq;
import xyz.khamim.slash.ecommerce.payload.PromotionReq;

import java.util.Arrays;
import java.util.List;

public class PromotionManagementStep {

    @Autowired
    private TestRestTemplate restTemplate;

    private String token = null;

    private String promotionId = null;

    private String productId = null;

    private List<String> promotionProductIds = null;

    @DataTableType
    public PromotionReq definePromotionDetails(DataTable dataTable) {
        List<String> row = dataTable.row(1);
        return PromotionReq.builder()
                .name(row.get(0))
                .discount(Integer.parseInt(row.get(1)))
                .build();
    }

    @Before
    public void createProduct() {
        ProductReq product = ProductReq.builder()
                .name("Product 1")
                .category("Category 1")
                .price(100)
                .build();
        productId = DataPreparationUtil.createProduct(restTemplate, product);
    }

    @Given("I am logged in as a Promotion Manager")
    public void iAmLoggedInAsAPromotionManager() {

        token = DataPreparationUtil.authenticate(restTemplate, Constant.PROMOTION_MANAGER_USERNAME, Constant.PASSWORD);
        Assertions.assertNotNull(token);
    }

    @When("I create a new promotion with the following details:")
    public void iCreateANewPromotionWithTheFollowingDetails(PromotionReq promotion) {
        promotion.setProductIds(List.of(productId));

        final IdResponse response = RestUtil.post(restTemplate, "/promotion/create", promotion, IdResponse.class, token);
        Assertions.assertNotNull(response);

        promotionId = response.getId();
    }

    @Then("the promotion should be created successfully")
    public void thePromotionShouldBeCreatedSuccessfully() {

        Assertions.assertNotNull(promotionId);
    }

    @Given("there is an existing promotion with name {string}")
    public void thereIsAnExistingPromotionWithName(String promotionName) {

        PromotionResponse[] response = RestUtil.get(restTemplate, "/promotion/all",
                PromotionResponse[].class);
        Assertions.assertNotNull(response);

        List<PromotionResponse> list = Arrays.stream(response)
                .filter(e -> promotionName.equals(e.getName()))
                .toList();
        Assertions.assertFalse(list.isEmpty());
        promotionId = list.get(0).getId();
        promotionProductIds = list.get(0).getProductIds();
    }

    @When("I update the promotion with the following details:")
    public void iUpdateThePromotionWithTheFollowingDetails(PromotionReq promotion) {

        promotion.setProductIds(promotionProductIds);

        final IdResponse response = RestUtil.post(restTemplate, "/promotion/update/"+promotionId, 
                promotion, IdResponse.class, token);
        Assertions.assertNotNull(response);
        promotionId = response.getId();
        Assertions.assertNotNull(promotionId);
    }

    @Then("the promotion should be updated successfully")
    public void thePromotionShouldBeUpdatedSuccessfully() {

        PromotionResponse response = RestUtil.get(restTemplate, "/promotion/get/"+promotionId,
                PromotionResponse.class);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getDiscount(), 10);
    }
}
