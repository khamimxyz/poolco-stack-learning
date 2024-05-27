package xyz.khamim.slash.ecommerce.cucumber.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import xyz.khamim.slash.ecommerce.graphql.input.OrderProductReq;
import xyz.khamim.slash.ecommerce.graphql.input.OrderReq;
import xyz.khamim.slash.ecommerce.graphql.input.ProductReq;
import xyz.khamim.slash.ecommerce.graphql.util.GraphQLReqBuilder;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class OrderStep {

    @Autowired
    protected WebTestClient client;
    private String orderId = null;

    @DataTableType
    public Map<String, Object> defineProduct(DataTable dataTable) {
        List<String> row = dataTable.row(1);
        ProductReq product = ProductReq.builder()
                .name(row.get(0))
                .category(row.get(1))
                .price(Integer.parseInt(row.get(2)))
                .build();

        return Map.of("product", product, "qty", Integer.parseInt(row.get(3)));
    }

    @Given("I am logged in as a Customer")
    public void iAmLoggedInAsAOrderManager() {}

    @When("I create a new order with the following details:")
    public void iCreateANewOrderWithTheFollowingDetails(Map<String, Object> map) {

        ProductReq product = (ProductReq) map.get("product");

        OrderReq order = OrderReq.builder()
          .customerName("mims")
          .orderProducts(
            List.of(OrderProductReq.builder()
              .productId("1")
              .productName(product.getName())
              .qty((Integer) map.get("qty"))
              .price(product.getPrice())
              .build())
          )
          .build();

        final String req = "{ \"query\": \""+new GraphQLReqBuilder()
          .mutation()
          .operation("checkout", Map.of("orderReq", order))
          .fields("id")
          .build()+"\"}";

        client.post().uri("/graphql")
          .contentType(MediaType.APPLICATION_JSON)
          .bodyValue(req)
          .exchange()
          .expectStatus().isOk()
          .expectBody(Map.class)
          .value(resultMap -> {
              Map<String, Map<String, Object>> data = (Map<String, Map<String, Object>>) resultMap.get("data");
              org.assertj.core.api.Assertions.assertThat(data).isNotNull();
              Map<String, Object> orderMap = data.get("checkout");
              orderId = (String) orderMap.get("id");
          });
    }

    @Then("the order should be created successfully")
    public void theOrderShouldBeCreatedSuccessfully() {

        Assertions.assertNotNull(orderId);
    }
}
