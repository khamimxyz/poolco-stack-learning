package xyz.khamim.slash.ecommerce.graphql.api;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import xyz.khamim.slash.ecommerce.graphql.input.OrderProductReq;
import xyz.khamim.slash.ecommerce.graphql.input.OrderReq;
import xyz.khamim.slash.ecommerce.graphql.repository.OrderRepository;
import xyz.khamim.slash.ecommerce.graphql.security.SecureMethodInterceptor;

import java.util.List;
import java.util.Map;
import xyz.khamim.slash.ecommerce.graphql.util.GraphQLReqBuilder;

@SuppressWarnings("unchecked")
@Slf4j
public class OrderControllerMutationIntegrationTest extends BaseDgsIntegrationTest {

  @Autowired
  private OrderRepository orderRepository;

  @MockBean
  private SecureMethodInterceptor secureMethodInterceptor;

  @Test
  public void testCheckout() {

    final String req = "{ \"query\": \""+new GraphQLReqBuilder()
      .mutation()
      .operation("checkout", Map.of("orderReq",
        OrderReq.builder()
          .customerName("mims")
          .orderProducts(List.of(
            OrderProductReq.builder()
              .productId("1")
              .productName("product1")
              .qty(1)
              .price(100)
              .build()
          ))
          .build()))
      .fields("createdBy")
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
        Map<String, Object> order = data.get("checkout");
        Assertions.assertThat(order.get("createdBy")).isEqualTo("mims");
      });
  }
}
