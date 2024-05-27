package xyz.khamim.slash.ecommerce.graphql.api;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import xyz.khamim.slash.ecommerce.graphql.model.Product;
import xyz.khamim.slash.ecommerce.graphql.model.Review;
import xyz.khamim.slash.ecommerce.graphql.repository.ProductRepository;
import xyz.khamim.slash.ecommerce.graphql.repository.ReviewRepository;
import xyz.khamim.slash.ecommerce.graphql.util.GraphQLReqBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Slf4j
public class ProductControllerQueryIntegrationTest extends BaseDgsIntegrationTest {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ReviewRepository reviewRepository;

  private final String current = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

  private final List<Product> products = List.of(
    Product.builder().name("product1").category("category1")
      .price(100).createdDate(current).build(),
    Product.builder().name("product2").category("category2")
      .price(50).createdDate(current).build()
  );

  private final List<Review> reviews = List.of(
    Review.builder().productId(products.get(0).getId())
      .reviewerName("reviewer1").star(3).build(),
    Review.builder().productId(products.get(0).getId())
      .reviewerName("reviewer2").star(5).build()
  );

  @BeforeEach
  @Override
  protected void setUp() {
    super.setUp();
    reviews.forEach(review -> {
      review.setPk("product");
      review.setSk(review.getProductId()+"#review#"+review.getId());
    });
    products.forEach(productRepository::create);
    reviews.forEach(reviewRepository::create);
  }

  @Test
  void testGetProduct() {

    final String id = products.get(0).getId();

    final String req = "{ \"query\": \""+new GraphQLReqBuilder()
      .query()
      .operation("getProduct", Map.of("id", id))
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
        Map<String, Object> product = data.get("getProduct");
        Assertions.assertThat(product.size()).isEqualTo(2);
        Assertions.assertThat(product.get("id")).isEqualTo(id);
        Assertions.assertThat(product.get("name")).isEqualTo("product1");
      });
  }

  @Test
  void testGetProductWithReviews() {

    final String id = products.get(0).getId();

    Map<String, List<String>> productResult = Map.of("product", List.of("id", "name"));
    Map<String, List<String>> reviewResult = Map.of("reviews",
      List.of("star", "reviewerName"));

    final String req = "{ \"query\": \""+new GraphQLReqBuilder()
      .query()
      .operation("getProductWithReviews", Map.of("id", id))
      .fields(productResult, reviewResult)
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
        Map<String, Object> productWithReviews = data.get("getProductWithReviews");
        Map<String, Object> product = (Map<String, Object>) productWithReviews.get("product");
        List<Object> reviews = (List<Object>) productWithReviews.get("reviews");
        Assertions.assertThat(product.size()).isEqualTo(2);
        Assertions.assertThat(product.get("id")).isEqualTo(id);
        Assertions.assertThat(product.get("name")).isEqualTo("product1");
        Assertions.assertThat(reviews.size()).isEqualTo(2);

        Assertions.assertThat(reviews).usingRecursiveComparison()
          .ignoringCollectionOrder()
          .isEqualTo(List.of(
            Map.of("star", 3, "reviewerName", "reviewer1"),
            Map.of("star", 5, "reviewerName", "reviewer2")
          ));

      });
  }

  @Test
  void testGetAllProducts() {

    final String id = products.get(0).getId();

    final String req = "{ \"query\": \""+new GraphQLReqBuilder()
      .query()
      .operation("getAllProducts", Map.of())
      .fields("name", "category")
      .build()+"\"}";

    webTestClient.post().uri("/graphql")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(req)
      .exchange()
      .expectStatus().isOk()
      .expectBody(Map.class)
      .value(map -> {
        Map<String, ?> data = (Map<String, Object>) map.get("data");
        Assertions.assertThat(data).isNotNull();
        List<Map<String, Object>> products = (List<Map<String, Object>>) data.get("getAllProducts");
        Assertions.assertThat(products.size()).isEqualTo(2);

        List<Map<String, Object>> expected = List.of(
          Map.of("name", "product1", "category", "category1"),
          Map.of("name", "product2", "category", "category2")
        );

        Assertions.assertThat(products).usingRecursiveComparison()
          .ignoringCollectionOrder()
          .isEqualTo(expected);
      });
  }
}
