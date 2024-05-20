package xyz.khamim.slash.ecommerce.graphql.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.khamim.slash.ecommerce.graphql.constant.DynamoDbConstant;
import xyz.khamim.slash.ecommerce.graphql.dto.ProductWithReviewDto;
import xyz.khamim.slash.ecommerce.graphql.input.FilterProductReq;
import xyz.khamim.slash.ecommerce.graphql.input.SortReq;
import xyz.khamim.slash.ecommerce.graphql.mock.TestDataFixture;
import xyz.khamim.slash.ecommerce.graphql.repository.util.ProductQueryUtil;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
class ProductRepositoryTest {

  @Mock
  private AmazonDynamoDB amazonDynamoDB;

  @InjectMocks
  private ProductRepository repository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getAll_NoFilterShouldUseDefaultQuery() {

    final QueryRequest expected = ProductQueryUtil.getQueryWithoutFilter();

    when(amazonDynamoDB.query(any(QueryRequest.class)))
      .thenReturn(new QueryResult().withItems(Map.of()));

    repository.getAll(null);
    ArgumentCaptor<QueryRequest> queryRequestCaptor = ArgumentCaptor.forClass(QueryRequest.class);
    verify(amazonDynamoDB).query(queryRequestCaptor.capture());
    final QueryRequest actual = queryRequestCaptor.getValue();

    Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void getAll_NoFilterWithSortShouldUseSortQuery() {

    final SortReq sortReq = SortReq.builder().
      sortBy(DynamoDbConstant.PRICE_ATTR)
      .sortType(DynamoDbConstant.DESC_SORT)
      .build();

    final QueryRequest expected = ProductQueryUtil.getQueryWithoutFilter(sortReq);

    when(amazonDynamoDB.query(any(QueryRequest.class)))
      .thenReturn(new QueryResult().withItems(Map.of()));

    final FilterProductReq req = FilterProductReq.builder()
      .sortReqList(List.of(sortReq))
      .build();

    repository.getAll(req);
    ArgumentCaptor<QueryRequest> queryRequestCaptor = ArgumentCaptor.forClass(QueryRequest.class);
    verify(amazonDynamoDB).query(queryRequestCaptor.capture());
    final QueryRequest actual = queryRequestCaptor.getValue();

    Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void getAll_WithCategoryFilterShouldUseDefaultSortQuery() {

    final String category = "Handphone";

    final QueryRequest expected = ProductQueryUtil.getQueryByCategory(category);

    when(amazonDynamoDB.query(any(QueryRequest.class)))
      .thenReturn(new QueryResult().withItems(Map.of()));

    final FilterProductReq req = FilterProductReq.builder()
      .category(category)
      .build();

    repository.getAll(req);
    ArgumentCaptor<QueryRequest> queryRequestCaptor = ArgumentCaptor.forClass(QueryRequest.class);
    verify(amazonDynamoDB).query(queryRequestCaptor.capture());
    final QueryRequest actual = queryRequestCaptor.getValue();

    Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void getAll_WithCategoryFilterSortShouldUseSortQuery() {

    final String category = "Handphone";

    final SortReq sortReq = SortReq.builder()
      .sortBy(DynamoDbConstant.PRICE_ATTR)
      .sortType(DynamoDbConstant.ASC_SORT)
      .build();

    final QueryRequest expected = ProductQueryUtil.getQueryByCategory(category, sortReq);

    when(amazonDynamoDB.query(any(QueryRequest.class)))
      .thenReturn(new QueryResult().withItems(Map.of()));

    final FilterProductReq req = FilterProductReq.builder()
      .category(category)
      .sortReqList(List.of(sortReq))
      .build();

    repository.getAll(req);
    ArgumentCaptor<QueryRequest> queryRequestCaptor = ArgumentCaptor.forClass(QueryRequest.class);
    verify(amazonDynamoDB).query(queryRequestCaptor.capture());
    final QueryRequest actual = queryRequestCaptor.getValue();

    Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void getProductWithReviews_ShouldRetrieveProductAndItsReviews() {

    ProductWithReviewDto expected = TestDataFixture.getSampleOfProductWithReviewsDto();

    QueryResult result = new QueryResult()
      .withItems(
        TestDataFixture.getSampleOfProductItemAttribute(
          expected.getProduct().getId(), expected.getProduct().getPk(),
          expected.getProduct().getSk()
        ),
        TestDataFixture.getSampleOfReviewItemAttribute(
          "1", expected.getProduct().getPk(), expected.getProduct().getId()+"#review#1"
        ),
        TestDataFixture.getSampleOfReviewItemAttribute(
          "2", expected.getProduct().getPk(), expected.getProduct().getId()+"#review#2"
        )
      );

    when(amazonDynamoDB.query(any(QueryRequest.class))).thenReturn(result);

    ProductWithReviewDto dto = repository.getProductWithReviews(expected.getProduct().getId());

    Assertions.assertThat(dto).usingRecursiveComparison().isEqualTo(expected);
  }
}