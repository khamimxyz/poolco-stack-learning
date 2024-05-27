package xyz.khamim.slash.ecommerce.graphql.api;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import xyz.khamim.slash.ecommerce.graphql.model.Order;
import xyz.khamim.slash.ecommerce.graphql.model.OrderData;
import xyz.khamim.slash.ecommerce.graphql.model.OrderProduct;
import xyz.khamim.slash.ecommerce.graphql.repository.OrderRepository;
import xyz.khamim.slash.ecommerce.graphql.util.GraphQLReqBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Slf4j
public class OrderControllerQueryIntegrationTest extends BaseDgsIntegrationTest {

  @Autowired
  private OrderRepository orderRepository;

  private final Order sampleOrder = Order.builder()
    .createdBy("mims")
    .createdDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))
    .orderData(
      OrderData.builder()
        .orderProducts(List.of(
          OrderProduct.builder()
            .productId("1")
            .productName("product1")
            .price(100)
            .qty(1)
            .build(),
          OrderProduct.builder()
            .productId("2")
            .productName("product2")
            .price(50)
            .qty(1)
            .build()
        ))
        .build())
    .build();

  @BeforeEach
  @Override
  protected void setUp() {
    super.setUp();
    orderRepository.create(sampleOrder);
  }

  @Test
  void testGetOrder() {

    final String req = "{ \"query\": \""+new GraphQLReqBuilder()
      .query()
      .operation("getOrder", Map.of("id", sampleOrder.getId()))
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
        Map<String, Object> order = data.get("getOrder");
        Assertions.assertThat(order.get("createdBy")).isEqualTo(sampleOrder.getCreatedBy());
      });
  }
}
