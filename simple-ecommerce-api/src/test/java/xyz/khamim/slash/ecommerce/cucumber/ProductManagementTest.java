package xyz.khamim.slash.ecommerce.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/product",
        glue = "xyz.khamim.slash.ecommerce.cucumber.steps"
)
@SpringBootTest
public class ProductManagementTest {}
