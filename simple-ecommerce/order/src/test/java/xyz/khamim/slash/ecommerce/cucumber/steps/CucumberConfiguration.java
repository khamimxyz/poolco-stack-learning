package xyz.khamim.slash.ecommerce.cucumber.steps;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.khamim.slash.ecommerce.graphql.EcommerceOrderApplication;

@CucumberContextConfiguration
@SpringBootTest(
        classes = EcommerceOrderApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class CucumberConfiguration {}