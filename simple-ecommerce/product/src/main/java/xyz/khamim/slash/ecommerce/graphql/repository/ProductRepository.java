package xyz.khamim.slash.ecommerce.graphql.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import org.springframework.stereotype.Repository;
import xyz.khamim.slash.ecommerce.graphql.dto.ProductWithReviewDto;
import xyz.khamim.slash.ecommerce.graphql.input.FilterProductReq;
import xyz.khamim.slash.ecommerce.graphql.model.Product;
import xyz.khamim.slash.ecommerce.graphql.model.Review;
import xyz.khamim.slash.ecommerce.graphql.repository.util.ModelMapper;
import xyz.khamim.slash.ecommerce.graphql.repository.util.ProductQueryUtil;
import xyz.khamim.slash.ecommerce.graphql.util.DynamoDbTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepository extends DynamoDbRepository<Product> {

  public ProductRepository(final AmazonDynamoDB amazonDynamoDB, final DynamoDbTable table) {
    super(amazonDynamoDB, table);
  }

  public List<Product> getAll(FilterProductReq req) {

    final List<Product> list = new ArrayList<>();

    QueryRequest queryRequest;

    if(req == null) {
      queryRequest = ProductQueryUtil.getQueryWithoutFilter(table.getTableName());
    } else {
      if (req.getCategory() == null) {
        if (req.getSortReqList() == null) {
          queryRequest = ProductQueryUtil.getQueryWithoutFilter(table.getTableName());
        } else {
          queryRequest = ProductQueryUtil.getQueryWithoutFilter(
            req.getSortReqList().get(0), table.getTableName());
        }
      } else {
        if (req.getSortReqList() == null) {
          queryRequest = ProductQueryUtil.getQueryByCategory(
            req.getCategory(), table.getTableName());
        } else {
          queryRequest = ProductQueryUtil.getQueryByCategory(
            req.getCategory(), req.getSortReqList().get(0), table.getTableName());
        }
      }
    }

    final QueryResult result = amazonDynamoDB.query(queryRequest);
    result.getItems().forEach(item -> list.add(ModelMapper.mapToModel(item, getEntityClass())));

    return list;
  }

  public ProductWithReviewDto getProductWithReviews(String id) {

    final QueryRequest queryRequest = new QueryRequest()
      .withTableName(table.getTableName())
      .withKeyConditionExpression("#pk = :pkVal AND begins_with(#sk, :skVal)")
      .withExpressionAttributeNames(Map.of("#pk", "pk", "#sk", "sk"))
      .withExpressionAttributeValues(
        Map.of(
          ":pkVal", new AttributeValue().withS("product"),
          ":skVal", new AttributeValue().withS(id)
        )
      );

    final QueryResult result = amazonDynamoDB.query(queryRequest);
    final ProductWithReviewDto dto = new ProductWithReviewDto();
    final List<Map<String, AttributeValue>> items = result.getItems();
    final List<Review> reviews = new ArrayList<>();
    for (Map<String, AttributeValue> item : items) {
      final String sk = item.get("sk").getS();
      if (!sk.contains("review")) {
        dto.setProduct(ModelMapper.mapToModel(item, Product.class));
      } else {
        reviews.add(ModelMapper.mapToModel(item, Review.class));
      }
    }
    dto.setReviews(reviews);

    return dto;
  }
}
