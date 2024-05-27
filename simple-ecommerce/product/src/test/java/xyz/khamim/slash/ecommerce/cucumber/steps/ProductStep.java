package xyz.khamim.slash.ecommerce.cucumber.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import xyz.khamim.slash.ecommerce.graphql.input.ProductReq;
import xyz.khamim.slash.ecommerce.graphql.security.SecureMethodInterceptor;
import xyz.khamim.slash.ecommerce.graphql.util.GraphQLReqBuilder;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ProductStep {

    @Autowired
    protected WebTestClient webTestClient;

    private String productId = null;

    @MockBean
    private SecureMethodInterceptor secureMethodInterceptor;

    @DataTableType
    public ProductReq defineProductDetails(DataTable dataTable) {
        List<String> row = dataTable.row(1);
        return ProductReq.builder()
                .name(row.get(0))
                .category(row.get(1))
                .price(Integer.parseInt(row.get(2)))
                .build();
    }

    @Given("I am logged in as a Product Manager")
    public void iAmLoggedInAsProductManager() {}

    @When("I create a new product with the following details:")
    public void iCreateANewProductWithFollowingDetails(ProductReq product) {
        final String req = "{ \"query\": \""+new GraphQLReqBuilder()
          .mutation()
          .operation("addProduct", Map.of("productReq", product))
          .fields("id")
          .build()+"\"}";

        webTestClient.post().uri("/graphql")
          .contentType(MediaType.APPLICATION_JSON)
          .bodyValue(req)
          .exchange()
          .expectStatus().isOk()
          .expectBody(Map.class)
          .value(map -> {
              Map<String, Map<String, Object>> data = (Map<String, Map<String, Object>>) map.get("data");
              org.assertj.core.api.Assertions.assertThat(data).isNotNull();
              Map<String, Object> resultMap = data.get("addProduct");
              productId = (String) resultMap.get("id");
          });
    }

    @Then("the product should be created successfully")
    public void productShouldBeCreatedSuccessfully() {
        Assertions.assertNotNull(productId);
    }
}
