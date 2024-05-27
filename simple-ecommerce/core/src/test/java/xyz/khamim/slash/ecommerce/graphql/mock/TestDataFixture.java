package xyz.khamim.slash.ecommerce.graphql.mock;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.khamim.slash.ecommerce.graphql.input.OrderProductReq;
import xyz.khamim.slash.ecommerce.graphql.input.OrderReq;
import xyz.khamim.slash.ecommerce.graphql.model.*;

import java.util.List;
import java.util.Map;

public class TestDataFixture {

  public static Product getSampleOfProduct() {

    return Product.builder()
      .name("Samsung Galaxy")
      .category("Handphone")
      .price(100)
      .createdDate("20240516131500")
      .build();
  }

  public static Map<String, AttributeValue> getSampleOfProductItemAttribute(String id, String pk, String sk) {

    return Map.of(
      "id", new AttributeValue().withS(id),
      "pk", new AttributeValue().withS(pk),
      "sk", new AttributeValue().withS(sk),
      "name", new AttributeValue().withS("Samsung Galaxy"),
      "category", new AttributeValue().withS("Handphone"),
      "price", new AttributeValue().withN("100"),
      "createdDate", new AttributeValue().withS("20240516131500")
    );
  }

  public static Review getSampleOfReview() {

    return Review.builder()
      .productId("2588f64c1f4a41ca8a81ab73b513c13b")
      .star(5)
      .reviewerName("mims")
      .build();
  }

  public static Map<String, AttributeValue> getSampleOfReviewItemAttribute(String id, String pk, String sk) {

    return Map.of(
      "id", new AttributeValue().withS(id),
      "pk", new AttributeValue().withS(pk),
      "sk", new AttributeValue().withS(sk),
      "productId", new AttributeValue().withS("2588f64c1f4a41ca8a81ab73b513c13b"),
      "star", new AttributeValue().withN("5"),
      "reviewerName", new AttributeValue().withS("mims")
    );
  }

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

  public static Map<String, AttributeValue> getSampleOfOrderItemAttribute(
    String id, String pk, String sk) throws Exception {

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(getSampleOfOrderData());

    return Map.of(
      "id", new AttributeValue().withS(id),
      "pk", new AttributeValue().withS(pk),
      "sk", new AttributeValue().withS(sk),
      "createdBy", new AttributeValue().withS("mims"),
      "createdDate", new AttributeValue().withS("20240516133000"),
      "orderData", new AttributeValue().withS(json)
    );
  }
}
