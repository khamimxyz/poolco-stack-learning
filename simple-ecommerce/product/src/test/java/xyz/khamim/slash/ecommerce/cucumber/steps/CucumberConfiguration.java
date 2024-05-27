package xyz.khamim.slash.ecommerce.cucumber.steps;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.khamim.slash.ecommerce.graphql.EcommerceProductApplication;

@CucumberContextConfiguration
@SpringBootTest(
        classes = EcommerceProductApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class CucumberConfiguration {}