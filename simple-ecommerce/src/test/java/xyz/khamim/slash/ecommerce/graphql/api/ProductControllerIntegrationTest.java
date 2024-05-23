package xyz.khamim.slash.ecommerce.graphql.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import xyz.khamim.slash.ecommerce.graphql.security.SecureMethodInterceptor;
import xyz.khamim.slash.ecommerce.graphql.service.ProductService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SuppressWarnings("unchecked")
class ProductControllerIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private ProductService productService;

  @MockBean
  private SecureMethodInterceptor secureMethodInterceptor;
  @Test
  void getProduct() {
  }

  @Test
  void getProductWithReviews() {
  }

  @Test
  void getAllProducts() {
  }

  @Test
  public void testAddProduct() {
    Mockito.doNothing().when(secureMethodInterceptor).beforeSecureMethod(Mockito.any(), Mockito.any());
  }
  @Test
  void addReview() {
  }
}