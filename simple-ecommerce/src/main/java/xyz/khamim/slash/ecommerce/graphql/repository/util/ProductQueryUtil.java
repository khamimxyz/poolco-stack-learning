package xyz.khamim.slash.ecommerce.graphql.repository.util;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import xyz.khamim.slash.ecommerce.graphql.constant.DynamoDbConstant;
import xyz.khamim.slash.ecommerce.graphql.input.SortReq;

import java.util.Map;

public class ProductQueryUtil {

  private static final String ENTITY_NAME = "product";

  public static QueryRequest getQueryWithoutFilter() {

    return getQueryWithoutFilter(null);
  }

  public static QueryRequest getQueryWithoutFilter(final SortReq sortReq) {

    final QueryRequest queryRequest = new QueryRequest()
      .withTableName(DynamoDbConstant.TABLE_NAME)
      .withKeyConditionExpression("#pk = :val")
      .withFilterExpression("attribute_exists(#attr)")
      .withExpressionAttributeNames(Map.of("#pk", "pk", "#attr", "category"))
      .withExpressionAttributeValues(
        Map.of(":val", new AttributeValue().withS(ENTITY_NAME))
      );

    if (sortReq != null) {
      final boolean scanIndexForward = DynamoDbConstant.ASC_SORT.equals(sortReq.getSortType());
      if(DynamoDbConstant.PRICE_ATTR.equals(sortReq.getSortBy())) {
        queryRequest.setIndexName(DynamoDbConstant.PRICE_SORT_INDEX_NAME);
      } else if(DynamoDbConstant.CREATED_DATE_ATTR.equals(sortReq.getSortBy())) {
        queryRequest.setIndexName(DynamoDbConstant.CREATED_DATE_SORT_INDEX_NAME);
      }
      queryRequest.setScanIndexForward(scanIndexForward);
    }

    return queryRequest;
  }

  public static QueryRequest getQueryByCategory(final String category) {

    return getQueryByCategory(category,
      SortReq.builder()
        .sortBy(DynamoDbConstant.CREATED_DATE_ATTR)
        .sortType(DynamoDbConstant.DESC_SORT)
        .build()
    );
  }

  public static QueryRequest getQueryByCategory(final String category, final SortReq sortReq) {
    final QueryRequest queryRequest = new QueryRequest()
      .withTableName(DynamoDbConstant.TABLE_NAME)
      .withKeyConditionExpression("#category = :val")
      .withExpressionAttributeNames(Map.of("#category", "category"))
      .withExpressionAttributeValues(
        Map.of(":val", new AttributeValue().withS(category))
      );

    final boolean scanIndexForward = DynamoDbConstant.ASC_SORT.equals(sortReq.getSortType());
    if (DynamoDbConstant.PRICE_ATTR.equals(sortReq.getSortBy())) {
      queryRequest.setIndexName(DynamoDbConstant.PRICE_INDEX_NAME);
    } else if (DynamoDbConstant.CREATED_DATE_ATTR.equals(sortReq.getSortBy())) {
      queryRequest.setIndexName(DynamoDbConstant.CREATED_DATE_INDEX_NAME);
    }
    queryRequest.setScanIndexForward(scanIndexForward);

    return queryRequest;
  }
}
