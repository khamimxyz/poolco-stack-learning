package xyz.khamim.slash.ecommerce.cucumber.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import xyz.khamim.slash.ecommerce.model.Product;

import java.util.List;

public class ProductManagementStep {

    @Autowired
    private WebTestClient webTestClient;

    @DataTableType
    public Product defineProductDetails(DataTable dataTable) {
        List<String> row = dataTable.row(1);
        return Product.builder()
                .name(row.get(0))
                .category(row.get(1))
                .price(Integer.parseInt(row.get(2)))
                .build();
    }

    @Given("I am logged in as a Product Manager")
    public void iAmLoggedInAsProductManager() {
        // Implementation for logging in as a Product Manager
    }

    @When("I create a new product with the following details:")
    public void iCreateANewProductWithFollowingDetails(Product product) {
        // Implementation for creating a new product
    }

    @Then("the product {string} should be created successfully")
    public void productShouldBeCreatedSuccessfully(String productName) {
        // Implementation to verify product creation
    }

    @Given("there is an existing product with name {string}")
    public void thereIsExistingProductWithName(String productName) {
        // Implementation to check if the product exists
    }

    @When("I update the product with name {string} with the following details:")
    public void iUpdateProductWithNameFollowingDetails(String productName, Product product) {
        // Implementation for updating an existing product
    }

    @Then("the product {string} should be updated successfully")
    public void productShouldBeUpdatedSuccessfully(String productName) {
        // Implementation to verify product update
    }
}
