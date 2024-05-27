package xyz.khamim.slash.ecommerce.graphql.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import xyz.khamim.slash.ecommerce.graphql.input.OrderReq;
import xyz.khamim.slash.ecommerce.graphql.model.Order;
import xyz.khamim.slash.ecommerce.graphql.repository.OrderRepository;
import xyz.khamim.slash.ecommerce.graphql.util.PayloadToModelConverter;

import xyz.khamim.slash.ecommerce.graphql.mock.TestDataFixture;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceTest {

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private PayloadToModelConverter converter;

  @InjectMocks
  private OrderService orderService;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void checkout_ShouldSuccessful() throws Exception {

    final OrderReq orderReq = TestDataFixture.getSampleOfOrderReq();
    final Order expected = TestDataFixture.getSampleOfOrder();

    when(converter.fromOrderProductReq(anyList()))
      .thenReturn(expected.getOrderData().getOrderProducts());
    when(orderRepository.create(any(Order.class))).thenReturn(expected);

    final Mono<Order> result = orderService.checkout(orderReq);

    StepVerifier.create(result)
      .expectNextMatches(order ->
          order.getCreatedBy().equals(expected.getCreatedBy()) &&
            order.getOrderData().getOrderProducts().equals(expected.getOrderData().getOrderProducts())
      )
      .verifyComplete();

    final ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
    verify(orderRepository).create(orderCaptor.capture());

    final Order actual = orderCaptor.getValue();
    Assertions.assertThat(actual.getCreatedBy()).isEqualTo(expected.getCreatedBy());
    Assertions.assertThat(actual.getOrderData())
      .usingRecursiveComparison()
      .isEqualTo(expected.getOrderData());
  }

  @Test
  void getOrder_ShouldSuccessful() {

    final Order order = TestDataFixture.getSampleOfOrder();
    when(orderRepository.get(anyString()))
      .thenReturn(order);

    final Mono<Order> result = orderService.getOrder(order.getId());

    StepVerifier.create(result)
      .expectNextMatches(o ->
        o.getCreatedBy().equals(order.getCreatedBy()) &&
          o.getOrderData().equals(order.getOrderData())
      )
      .verifyComplete();
  }
}