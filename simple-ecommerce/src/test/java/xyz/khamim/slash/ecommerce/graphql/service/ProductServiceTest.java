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
import xyz.khamim.slash.ecommerce.graphql.constant.DynamoDbConstant;
import xyz.khamim.slash.ecommerce.graphql.dto.ProductWithReviewDto;
import xyz.khamim.slash.ecommerce.graphql.input.FilterProductReq;
import xyz.khamim.slash.ecommerce.graphql.input.ProductReq;
import xyz.khamim.slash.ecommerce.graphql.input.ReviewReq;
import xyz.khamim.slash.ecommerce.graphql.input.SortReq;
import xyz.khamim.slash.ecommerce.graphql.mock.TestDataFixture;
import xyz.khamim.slash.ecommerce.graphql.model.Product;
import xyz.khamim.slash.ecommerce.graphql.model.Review;
import xyz.khamim.slash.ecommerce.graphql.repository.ProductRepository;
import xyz.khamim.slash.ecommerce.graphql.repository.ReviewRepository;
import xyz.khamim.slash.ecommerce.graphql.service.helper.PayloadToModelConverter;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ProductServiceTest {

  @Mock
  private PayloadToModelConverter converter;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private ReviewRepository reviewRepository;

  @InjectMocks
  private ProductService productService;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createProduct_ShouldCallRepositoryCreate() {

    final ProductReq productReq = TestDataFixture.getSampleOfProductReq();
    final Product expected = TestDataFixture.getSampleOfProduct();

    when(converter.fromProductReq(any(ProductReq.class), anyString()))
      .thenReturn(expected);
    when(productRepository.create(any(Product.class)))
      .thenReturn(expected);

    productService.createProduct(productReq).block();
    verify(productRepository, times(1)).create(any(Product.class));
  }

  @Test
  void createReview_ShouldSuccessfulAndUseCorrectPkAndSK() {

    final ReviewReq reviewReq = TestDataFixture.getSampleOfReviewReq();
    final Review expected = TestDataFixture.getSampleOfReview();
    expected.setPk("product");
    expected.setSk(expected.getProductId()+"#review#"+expected.getId());
    final Review converted = TestDataFixture.getSampleOfReview();
    converted.setId(expected.getId());

    when(converter.fromReviewReq(any(ReviewReq.class)))
      .thenReturn(converted);
    when(reviewRepository.create(any(Review.class))).thenReturn(expected);

    productService.createReview(reviewReq).block();

    final ArgumentCaptor<Review> reviewCaptor = ArgumentCaptor.forClass(Review.class);
    verify(reviewRepository).create(reviewCaptor.capture());

    final Review actual = reviewCaptor.getValue();
    Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void getProduct_ShouldReturnCorrectly() {

    final Product product = TestDataFixture.getSampleOfProduct();
    when(productRepository.get(anyString()))
      .thenReturn(product);

    final Mono<Product> result = productService.getProduct(product.getId());

    StepVerifier.create(result)
      .expectNextMatches(p -> p.equals(product))
      .verifyComplete();
  }

  @Test
  void getAllProducts_SortByPriceThenCreatedDateAscShouldReturnCorrectly() {

    final FilterProductReq req = FilterProductReq.builder()
      .sortReqList(List.of(
        SortReq.builder()
          .sortBy(DynamoDbConstant.PRICE_ATTR)
          .sortType(DynamoDbConstant.ASC_SORT)
          .build(),
        SortReq.builder()
          .sortBy(DynamoDbConstant.CREATED_DATE_ATTR)
          .sortType(DynamoDbConstant.ASC_SORT)
          .build()
      ))
      .build();

    when(productRepository.getAll(any(FilterProductReq.class)))
      .thenReturn(TestDataFixture.getSampleOfOrderedProductsByPrice());

    Mono<List<Product>> result = productService.getAllProducts(req);
    StepVerifier.create(result)
      .expectNextMatches(actual -> {
        List<Product> expected = TestDataFixture.getSampleOfOrderedProductsByPriceCreatedDateAsc();
        try {
          Assertions.assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFields("id", "pk", "sk")
            .isEqualTo(expected);
          return true;
        } catch (AssertionError e) {
          return false;
        }
      })
      .verifyComplete();
  }

  @Test
  void getAllProducts_SortByPriceThenCreatedDateDescShouldReturnCorrectly() {

    final FilterProductReq req = FilterProductReq.builder()
      .sortReqList(List.of(
        SortReq.builder()
          .sortBy(DynamoDbConstant.PRICE_ATTR)
          .sortType(DynamoDbConstant.ASC_SORT)
          .build(),
        SortReq.builder()
          .sortBy(DynamoDbConstant.CREATED_DATE_ATTR)
          .sortType(DynamoDbConstant.DESC_SORT)
          .build()
      ))
      .build();

    when(productRepository.getAll(any(FilterProductReq.class)))
      .thenReturn(TestDataFixture.getSampleOfOrderedProductsByPrice());

    Mono<List<Product>> result = productService.getAllProducts(req);
    StepVerifier.create(result)
      .expectNextMatches(actual -> {
        List<Product> expected = TestDataFixture.getSampleOfOrderedProductsByPriceCreatedDateDesc();
        try {
          Assertions.assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFields("id", "pk", "sk")
            .isEqualTo(expected);
          return true;
        } catch (AssertionError e) {
          return false;
        }
      })
      .verifyComplete();
  }

  @Test
  void getAllProducts_SortByCreatedDateThenPriceAscShouldReturnCorrectly() {

    final FilterProductReq req = FilterProductReq.builder()
      .sortReqList(List.of(
        SortReq.builder()
          .sortBy(DynamoDbConstant.CREATED_DATE_ATTR)
          .sortType(DynamoDbConstant.ASC_SORT)
          .build(),
        SortReq.builder()
          .sortBy(DynamoDbConstant.PRICE_ATTR)
          .sortType(DynamoDbConstant.ASC_SORT)
          .build()
      ))
      .build();

    when(productRepository.getAll(any(FilterProductReq.class)))
      .thenReturn(TestDataFixture.getSampleOfOrderedProductsByCreatedDate());

    Mono<List<Product>> result = productService.getAllProducts(req);
    StepVerifier.create(result)
      .expectNextMatches(actual -> {
        List<Product> expected = TestDataFixture.getSampleOfOrderedProductsByCreatedDatePriceAsc();
        try {
          Assertions.assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFields("id", "pk", "sk")
            .isEqualTo(expected);
          return true;
        } catch (AssertionError e) {
          return false;
        }
      })
      .verifyComplete();
  }

  @Test
  void getAllProducts_SortByCreatedDateThenPriceDescShouldReturnCorrectly() {

    final FilterProductReq req = FilterProductReq.builder()
      .sortReqList(List.of(
        SortReq.builder()
          .sortBy(DynamoDbConstant.CREATED_DATE_ATTR)
          .sortType(DynamoDbConstant.ASC_SORT)
          .build(),
        SortReq.builder()
          .sortBy(DynamoDbConstant.PRICE_ATTR)
          .sortType(DynamoDbConstant.DESC_SORT)
          .build()
      ))
      .build();

    when(productRepository.getAll(any(FilterProductReq.class)))
      .thenReturn(TestDataFixture.getSampleOfOrderedProductsByCreatedDate());

    Mono<List<Product>> result = productService.getAllProducts(req);
    StepVerifier.create(result)
      .expectNextMatches(actual -> {
        List<Product> expected = TestDataFixture.getSampleOfOrderedProductsByCreatedDatePriceDesc();
        try {
          Assertions.assertThat(actual)
            .usingRecursiveComparison()
            .ignoringFields("id", "pk", "sk")
            .isEqualTo(expected);
          return true;
        } catch (AssertionError e) {
          return false;
        }
      })
      .verifyComplete();
  }

  @Test
  void getProductWithReviews_ShouldCallRepository() {

    when(productRepository.getProductWithReviews(any(String.class)))
      .thenReturn(ProductWithReviewDto.builder().build());

    productService.getProductWithReviews("1").block();
    verify(productRepository, times(1))
      .getProductWithReviews(any(String.class));
  }
}