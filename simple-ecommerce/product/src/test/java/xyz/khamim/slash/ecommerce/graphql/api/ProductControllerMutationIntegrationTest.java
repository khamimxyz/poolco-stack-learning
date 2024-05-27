package xyz.khamim.slash.ecommerce.graphql.api;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import xyz.khamim.slash.ecommerce.graphql.input.ProductReq;
import xyz.khamim.slash.ecommerce.graphql.input.ReviewReq;
import xyz.khamim.slash.ecommerce.graphql.model.Product;
import xyz.khamim.slash.ecommerce.graphql.security.SecureMethodInterceptor;
import xyz.khamim.slash.ecommerce.graphql.service.FeedService;
import xyz.khamim.slash.ecommerce.graphql.util.GraphQLReqBuilder;

import java.util.Map;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

@SuppressWarnings("unchecked")
@Slf4j
public class ProductControllerMutationIntegrationTest extends BaseDgsIntegrationTest {

  @MockBean
  private SecureMethodInterceptor secureMethodInterceptor;

  @MockBean
  private FeedService feedService;

  @Test
  public void testAddProduct() {

    final String req = "{ \"query\": \""+new GraphQLReqBuilder()
      .mutation()
      .operation("addProduct", Map.of("productReq",
        ProductReq.builder()
          .name("product")
          .category("category 1")
          .price(100)
          .build()))
      .fields("id", "name")
      .build()+"\"}";

    webTestClient.post().uri("/graphql")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(req)
      .exchange()
      .expectStatus().isOk()
      .expectBody(Map.class)
      .value(map -> {
        Map<String, Map<String, Object>> data = (Map<String, Map<String, Object>>) map.get("data");
        Assertions.assertThat(data).isNotNull();
        Map<String, Object> product = data.get("addProduct");
        Assertions.assertThat(product.size()).isEqualTo(2);
        Assertions.assertThat(product.get("id")).isNotNull();
        Assertions.assertThat(product.get("name")).isEqualTo("product");
      });
  }

  @Test
  void testAddReview() {

    final String req = "{ \"query\": \""+new GraphQLReqBuilder()
      .mutation()
      .operation("addReview", Map.of("reviewReq",
        ReviewReq.builder()
          .productId("1")
          .reviewerName("mims")
          .star(5)
          .build()))
      .fields("reviewerName")
      .build()+"\"}";

    webTestClient.post().uri("/graphql")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(req)
      .exchange()
      .expectStatus().isOk()
      .expectBody(Map.class)
      .value(map -> {
        Map<String, Map<String, Object>> data = (Map<String, Map<String, Object>>) map.get("data");
        Assertions.assertThat(data).isNotNull();
        Map<String, Object> review = data.get("addReview");
        Assertions.assertThat(review.size()).isEqualTo(1);
        Assertions.assertThat(review.get("reviewerName")).isEqualTo("mims");
      });

    Mockito.verify(feedService, times(1))
      .sendRecommendationFeed(any(Product.class));
  }
}
