package xyz.khamim.slash.ecommerce.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/promotion",
        glue = "xyz.khamim.slash.ecommerce.cucumber.steps"
)
public class PromotionManagementTest {}
