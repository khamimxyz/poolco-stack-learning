package xyz.khamim.slash.ecommerce.graphql.repository.util;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.khamim.slash.ecommerce.graphql.mock.TestDataFixture;
import xyz.khamim.slash.ecommerce.graphql.model.Product;
import xyz.khamim.slash.ecommerce.graphql.model.Review;
import xyz.khamim.slash.ecommerce.graphql.model.order.Order;

import java.util.HashMap;
import java.util.Map;

class ModelMapperTest {

    @Test
    void mapToAttributes_ShouldMapProduct() {

      final Product product = TestDataFixture.getSampleOfProduct();

      final Map<String, AttributeValue> expected = TestDataFixture.getSampleOfProductItemAttribute(
        product.getId(), product.getPk(), product.getSk());

      final Map<String, AttributeValue> attributeMap = ModelMapper.mapToAttributes(product);

      Assertions.assertThat(attributeMap).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void mapToAttributes_ShouldMapReview() {

        final Review review = TestDataFixture.getSampleOfReview();

        final Map<String, AttributeValue> expected = TestDataFixture.getSampleOfReviewItemAttribute(
          review.getId(), review.getPk(), review.getSk());

        final Map<String, AttributeValue> attributeMap = ModelMapper.mapToAttributes(review);

        Assertions.assertThat(attributeMap).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void mapToAttributes_ShouldMapOrder() throws Exception {

      final Order order = TestDataFixture.getSampleOfOrder();

      final Map<String, AttributeValue> expected = TestDataFixture.getSampleOfOrderItemAttribute(
        order.getId(), order.getPk(), order.getSk());

      final Map<String, AttributeValue> attributeMap = ModelMapper.mapToAttributes(order);

      Assertions.assertThat(attributeMap).usingRecursiveComparison().isEqualTo(expected);
    }

  @Test
  void mapToAttributes_ShouldIgnoreNull() throws Exception {

    final Product product = TestDataFixture.getSampleOfProduct();
    product.setCategory(null);

    final Map<String, AttributeValue> expected = new HashMap<>(TestDataFixture.getSampleOfProductItemAttribute(
      product.getId(), product.getPk(), product.getSk()));
    expected.remove("category");

    final Map<String, AttributeValue> attributeMap = ModelMapper.mapToAttributes(product);

    Assertions.assertThat(attributeMap).usingRecursiveComparison().isEqualTo(expected);
  }

    @Test
    void mapToModel_ShouldMapToProduct() {

      String id = "2588f64c1f4a41ca8a81ab73b513c13b";
      String pk = "product";

      final Map<String, AttributeValue> item = TestDataFixture.getSampleOfProductItemAttribute(id, pk, id);

      Product expected = TestDataFixture.getSampleOfProduct();
      expected.setId(id);
      expected.setPk(pk);
      expected.setSk(id);

      Product product = ModelMapper.mapToModel(item, Product.class);

      Assertions.assertThat(product).usingRecursiveComparison().isEqualTo(expected);
    }

  @Test
  void mapToModel_ShouldMapToReview() {

    String id = "1588f64c1f4a41ca8a81ab73b513c131";
    String pk = "product";
    String sk = id+"#review#2588f64c1f4a41ca8a81ab73b513c13b";

    final Map<String, AttributeValue> item = TestDataFixture.getSampleOfReviewItemAttribute(id, pk, sk);

    Review expected = TestDataFixture.getSampleOfReview();
    expected.setId(id);
    expected.setPk(pk);
    expected.setSk(sk);

    Review review = ModelMapper.mapToModel(item, Review.class);

    Assertions.assertThat(review).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void mapToModel_ShouldMapToOrder() throws Exception {

    String id = "1588f64c1f4a41ca8a81ab73b513c131";
    String pk = "order";

    final Map<String, AttributeValue> item = TestDataFixture.getSampleOfOrderItemAttribute(id, pk, id);

    Order expected = TestDataFixture.getSampleOfOrder();
    expected.setId(id);
    expected.setPk(pk);
    expected.setSk(id);

    Order order = ModelMapper.mapToModel(item, Order.class);

    Assertions.assertThat(order).usingRecursiveComparison().isEqualTo(expected);
  }
}