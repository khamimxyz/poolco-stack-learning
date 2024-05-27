package xyz.khamim.slash.ecommerce.graphql.mock;

import xyz.khamim.slash.ecommerce.graphql.input.OrderProductReq;
import xyz.khamim.slash.ecommerce.graphql.input.OrderReq;
import xyz.khamim.slash.ecommerce.graphql.model.Order;
import xyz.khamim.slash.ecommerce.graphql.model.OrderData;
import xyz.khamim.slash.ecommerce.graphql.model.OrderProduct;

import java.util.List;

public class TestDataFixture {

  public static OrderReq getSampleOfOrderReq() {

    return OrderReq.builder()
      .customerName("mims")
      .orderProducts(
        List.of(
          OrderProductReq.builder()
            .productId("2588f64c1f4a41ca8a81ab73b513c13b")
            .productName("Samsung Galaxy Fold")
            .qty(1)
            .price(100)
            .build(),
          OrderProductReq.builder()
            .productId("1588f64c1f4a41ca8a81ab73b513c13a")
            .productName("Samsung Galaxy Tab")
            .qty(1)
            .price(50)
            .build()
        )
      )
      .build();
  }

  public static Order getSampleOfOrder() {

    return Order.builder()
      .createdBy("mims")
      .createdDate("20240516133000")
      .orderData(getSampleOfOrderData()).build();
  }

  private static OrderData getSampleOfOrderData() {

    return OrderData.builder()
      .orderProducts(
        List.of(
          OrderProduct.builder()
            .productId("2588f64c1f4a41ca8a81ab73b513c13b")
            .productName("Samsung Galaxy Fold")
            .qty(1)
            .price(100)
            .build(),
          OrderProduct.builder()
            .productId("1588f64c1f4a41ca8a81ab73b513c13a")
            .productName("Samsung Galaxy Tab")
            .qty(1)
            .price(50)
            .build()
        )
      ).build();
  }
}
