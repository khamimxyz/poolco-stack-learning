package xyz.khamim.slash.ecommerce.cucumber.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import xyz.khamim.slash.ecommerce.cucumber.Constant;
import xyz.khamim.slash.ecommerce.cucumber.payload.IdResponse;
import xyz.khamim.slash.ecommerce.cucumber.util.DataPreparationUtil;
import xyz.khamim.slash.ecommerce.cucumber.util.RestUtil;
import xyz.khamim.slash.ecommerce.payload.OrderProductReq;
import xyz.khamim.slash.ecommerce.payload.OrderReq;
import xyz.khamim.slash.ecommerce.payload.ProductReq;
import xyz.khamim.slash.ecommerce.payload.PromotionReq;

import java.util.List;
import java.util.Map;

public class OrderManagementStep {

    @Autowired
    private TestRestTemplate restTemplate;

    private String token = null;

    private String orderId = null;

    @DataTableType
    public Map<String, Object> definePromoAndProduct(DataTable dataTable) {
        List<String> row = dataTable.row(1);
        ProductReq product = ProductReq.builder()
                .name(row.get(0))
                .category(row.get(1))
                .price(Integer.parseInt(row.get(2)))
                .build();
        PromotionReq promotion = PromotionReq.builder()
                .name(row.get(3))
                .discount(Integer.parseInt(row.get(4)))
                .build();

        return Map.of("product", product, "promotion", promotion);
    }

    @Given("I am logged in as a Order Manager")
    public void iAmLoggedInAsAOrderManager() {

        token = DataPreparationUtil.authenticate(restTemplate, Constant.ORDER_MANAGER_USERNAME, Constant.PASSWORD);
        Assertions.assertNotNull(token);
    }

    @When("I create a new order with the following details:")
    public void iCreateANewOrderWithTheFollowingDetails(Map<String, Object> map) {

        ProductReq product = (ProductReq) map.get("product");
        String productId = DataPreparationUtil.createProduct(restTemplate, product);

        PromotionReq promotion = (PromotionReq) map.get("promotion");
        promotion.setProductIds(List.of(productId));

        String promotionId = DataPreparationUtil.createPromotion(restTemplate, promotion);

        OrderReq order = new OrderReq();
        order.setOrderProducts(List.of(
                OrderProductReq.builder()
                        .productId(productId)
                        .promotionId(promotionId)
                        .qty(1)
                        .price(product.getPrice())
                        .build()
        ));

        final IdResponse response = RestUtil.post(restTemplate, "/order/create", order,
                IdResponse.class, token);
        Assertions.assertNotNull(response);
        orderId = response.getId();
    }

    @Then("the order should be created successfully")
    public void theOrderShouldBeCreatedSuccessfully() {

        Assertions.assertNotNull(orderId);
    }
}
