package xyz.khamim.slash.ecommerce.graphql.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import xyz.khamim.slash.ecommerce.graphql.model.Product;
import xyz.khamim.slash.ecommerce.graphql.model.Review;
import xyz.khamim.slash.ecommerce.graphql.repository.util.ModelMapper;
import xyz.khamim.slash.ecommerce.graphql.util.DynamoDbTable;

import java.util.List;
import java.util.Map;
import xyz.khamim.slash.ecommerce.graphql.mock.TestDataFixture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DynamoDbRepositoryTest {

  @Mock
  private AmazonDynamoDB amazonDynamoDB;

  @Mock
  private DynamoDbTable table;

  @InjectMocks
  private ProductRepository repository;

  @InjectMocks
  private ReviewRepository reviewRepository;

  @BeforeEach
  void setUp() {

    MockitoAnnotations.openMocks(this);
    when(table.getTableName()).thenReturn("MyEcommerce");
  }

  @Test
  void create_ShouldCreateItemInDynamoDB() {

    Product item = TestDataFixture.getSampleOfProduct();

    PutItemResult putItemResult = new PutItemResult();
    when(amazonDynamoDB.putItem(Mockito.any(PutItemRequest.class)))
      .thenReturn(putItemResult);

    Product savedItem = repository.create(item);
    verify(amazonDynamoDB).putItem(any(PutItemRequest.class));

    assertEquals(item, savedItem);
  }

  @Test
  void get_ShouldRetrieveItemFromDynamoDB() {
    Product expectedItem = TestDataFixture.getSampleOfProduct();

    Map<String, AttributeValue> itemMap = ModelMapper.mapToAttributes(expectedItem);
    GetItemResult getItemResult = new GetItemResult().withItem(itemMap);

    when(amazonDynamoDB.getItem(any(GetItemRequest.class))).thenReturn(getItemResult);
    Product retrievedItem = repository.get(expectedItem.getId());
    verify(amazonDynamoDB).getItem(any(GetItemRequest.class));
    assertEquals(expectedItem, retrievedItem);
  }

  @Test
  @SuppressWarnings("unchecked")
  void getAll_ShouldRetrieveAllItemsFromDynamoDB() {
    Review review = TestDataFixture.getSampleOfReview();
    List<Review> expectedItems = List.of(review);

    QueryResult queryResult = new QueryResult()
      .withItems(TestDataFixture.getSampleOfReviewItemAttribute(
        review.getId(), review.getPk(), review.getSk()));

    when(amazonDynamoDB.query(any(QueryRequest.class)))
      .thenReturn(queryResult);

    List<Review> reviews = reviewRepository.getAll();

    Assertions.assertThat(reviews).usingRecursiveComparison().isEqualTo(expectedItems);
  }

  private static class ProductRepository extends DynamoDbRepository<Product> {
    public ProductRepository(AmazonDynamoDB amazonDynamoDB, DynamoDbTable table) {
      super(amazonDynamoDB, table);
    }
  }

  private static class ReviewRepository extends DynamoDbRepository<Review> {
    public ReviewRepository(AmazonDynamoDB amazonDynamoDB, DynamoDbTable table) {
      super(amazonDynamoDB, table);
    }
  }
}