package xyz.khamim.slash.ecommerce.graphql.mock;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.xspec.L;
import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.khamim.slash.ecommerce.graphql.dto.ProductWithReviewDto;
import xyz.khamim.slash.ecommerce.graphql.input.OrderProductReq;
import xyz.khamim.slash.ecommerce.graphql.input.OrderReq;
import xyz.khamim.slash.ecommerce.graphql.input.ProductReq;
import xyz.khamim.slash.ecommerce.graphql.input.ReviewReq;
import xyz.khamim.slash.ecommerce.graphql.model.Product;
import xyz.khamim.slash.ecommerce.graphql.model.Review;
import xyz.khamim.slash.ecommerce.graphql.model.order.Order;
import xyz.khamim.slash.ecommerce.graphql.model.order.OrderData;
import xyz.khamim.slash.ecommerce.graphql.model.order.OrderProduct;
import xyz.khamim.slash.ecommerce.graphql.service.helper.ProductComparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class TestDataFixture {

  public static ProductReq getSampleOfProductReq() {

    return ProductReq.builder()
      .name("Samsung Galaxy")
      .category("Handphone")
      .price(100)
      .build();
  }

  public static Product getSampleOfProduct() {

    return Product.builder()
      .name("Samsung Galaxy")
      .category("Handphone")
      .price(100)
      .createdDate("20240516131500")
      .build();
  }

  public static List<Product> getSampleOfOrderedProductsByPrice() {

    return List.of(
      Product.builder()
        .name("Samsung Galaxy A1")
        .category("Handphone")
        .price(10)
        .createdDate("20240516131500")
        .build(),
      Product.builder()
        .name("Samsung Galaxy A1S")
        .category("Handphone")
        .price(10)
        .createdDate("20240514131500")
        .build(),
      Product.builder()
        .name("Samsung Galaxy A2")
        .category("Handphone")
        .price(20)
        .createdDate("20240515131500")
        .build(),
      Product.builder()
        .name("Samsung Galaxy A3")
        .category("Handphone")
        .price(30)
        .createdDate("20240517131500")
        .build()
    );
  }

  public static List<Product> getSampleOfOrderedProductsByCreatedDate() {

    return List.of(
      Product.builder()
        .name("Samsung Galaxy A1")
        .category("Handphone")
        .price(100)
        .createdDate("20240514131500")
        .build(),
      Product.builder()
        .name("Samsung Galaxy A1S")
        .category("Handphone")
        .price(120)
        .createdDate("20240514131500")
        .build(),
      Product.builder()
        .name("Samsung Galaxy A2")
        .category("Handphone")
        .price(50)
        .createdDate("20240515131500")
        .build(),
      Product.builder()
        .name("Samsung Galaxy A3")
        .category("Handphone")
        .price(150)
        .createdDate("20240516131500")
        .build()
    );
  }

  public static List<Product> getSampleOfOrderedProductsByPriceCreatedDateAsc() {

    return List.of(
      Product.builder()
        .name("Samsung Galaxy A1S")
        .category("Handphone")
        .price(10)
        .createdDate("20240514131500")
        .build(),
      Product.builder()
        .name("Samsung Galaxy A1")
        .category("Handphone")
        .price(10)
        .createdDate("20240516131500")
        .build(),
      Product.builder()
        .name("Samsung Galaxy A2")
        .category("Handphone")
        .price(20)
        .createdDate("20240515131500")
        .build(),
      Product.builder()
        .name("Samsung Galaxy A3")
        .category("Handphone")
        .price(30)
        .createdDate("20240517131500")
        .build()
    );
  }

  public static List<Product> getSampleOfOrderedProductsByPriceCreatedDateDesc() {

    return getSampleOfOrderedProductsByPrice();
  }

  public static List<Product> getSampleOfOrderedProductsByCreatedDatePriceAsc() {

    return getSampleOfOrderedProductsByCreatedDate();
  }

  public static List<Product> getSampleOfOrderedProductsByCreatedDatePriceDesc() {

    return List.of(
      Product.builder()
        .name("Samsung Galaxy A1S")
        .category("Handphone")
        .price(120)
        .createdDate("20240514131500")
        .build(),
      Product.builder()
        .name("Samsung Galaxy A1")
        .category("Handphone")
        .price(100)
        .createdDate("20240514131500")
        .build(),
      Product.builder()
        .name("Samsung Galaxy A2")
        .category("Handphone")
        .price(50)
        .createdDate("20240515131500")
        .build(),
      Product.builder()
        .name("Samsung Galaxy A3")
        .category("Handphone")
        .price(150)
        .createdDate("20240516131500")
        .build()
    );
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

  public static ProductWithReviewDto getSampleOfProductWithReviewsDto() {

    Product product = TestDataFixture.getSampleOfProduct();

    Review review1 = TestDataFixture.getSampleOfReview();
    review1.setId("1");
    review1.setPk(product.getPk());
    review1.setSk(product.getId()+"#review#1");

    Review review2 = TestDataFixture.getSampleOfReview();
    review2.setId("2");
    review2.setPk(product.getPk());
    review2.setSk(product.getId()+"#review#2");

    return ProductWithReviewDto.builder()
      .product(product)
      .reviews(List.of(review1, review2))
      .build();
  }

  public static ReviewReq getSampleOfReviewReq() {

    return ReviewReq.builder()
      .productId("2588f64c1f4a41ca8a81ab73b513c13b")
      .star(5)
      .reviewerName("mims")
      .build();
  }
}
