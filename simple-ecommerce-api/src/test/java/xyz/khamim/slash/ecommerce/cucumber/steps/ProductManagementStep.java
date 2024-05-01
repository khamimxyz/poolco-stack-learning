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
import xyz.khamim.slash.ecommerce.cucumber.payload.ProductResponse;
import xyz.khamim.slash.ecommerce.cucumber.util.DataPreparationUtil;
import xyz.khamim.slash.ecommerce.cucumber.util.RestUtil;
import xyz.khamim.slash.ecommerce.payload.ProductReq;

import java.util.Arrays;
import java.util.List;

public class ProductManagementStep {

    @Autowired
    private TestRestTemplate restTemplate;

    private String token = null;

    private String productId = null;

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
    public void iAmLoggedInAsProductManager() {

        token = DataPreparationUtil.authenticate(restTemplate, Constant.PRODUCT_MANAGER_USERNAME, Constant.PASSWORD);
        Assertions.assertNotNull(token);
    }

    @When("I create a new product with the following details:")
    public void iCreateANewProductWithFollowingDetails(ProductReq product) {
        final IdResponse response = RestUtil.post(restTemplate, "/product/create", product,
                IdResponse.class, token);
        Assertions.assertNotNull(response);
        productId = response.getId();
    }

    @Then("the product should be created successfully")
    public void productShouldBeCreatedSuccessfully() {
        Assertions.assertNotNull(productId);
    }

    @Given("there is an existing product with name {string}")
    public void thereIsExistingProductWithName(String productName) {
        ProductResponse[] response = RestUtil.get(restTemplate, "/product/all",
                ProductResponse[].class);
        Assertions.assertNotNull(response);

        List<ProductResponse> list = Arrays.stream(response).filter(e -> productName.equals(e.getName())).toList();
        Assertions.assertFalse(list.isEmpty());
        productId = list.get(0).getId();
    }

    @When("I update the product with the following details:")
    public void iUpdateProductWithNameFollowingDetails(ProductReq product) {
        final IdResponse response = RestUtil.post(restTemplate, "/product/update/"+productId, product, IdResponse.class, token);
        Assertions.assertNotNull(response);
        productId = response.getId();
        Assertions.assertNotNull(productId);
    }

    @Then("the product should be updated successfully")
    public void productShouldBeUpdatedSuccessfully() {
        ProductResponse response = RestUtil.get(restTemplate, "/product/get/"+productId,
                ProductResponse.class);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getPrice(), 250);
    }
}
