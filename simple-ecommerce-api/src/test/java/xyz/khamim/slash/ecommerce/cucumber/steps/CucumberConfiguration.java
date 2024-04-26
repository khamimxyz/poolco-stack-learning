package xyz.khamim.slash.ecommerce.cucumber.steps;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.khamim.slash.ecommerce.EcommerceApplication;

@CucumberContextConfiguration
@SpringBootTest(
        classes = EcommerceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class CucumberConfiguration {}