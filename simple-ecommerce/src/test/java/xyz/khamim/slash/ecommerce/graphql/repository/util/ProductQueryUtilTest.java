package xyz.khamim.slash.ecommerce.graphql.repository.util;

import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import org.junit.jupiter.api.Test;
import xyz.khamim.slash.ecommerce.graphql.constant.DynamoDbConstant;
import xyz.khamim.slash.ecommerce.graphql.input.SortReq;
import xyz.khamim.slash.ecommerce.graphql.util.TestConstant;

import static org.junit.jupiter.api.Assertions.*;

class ProductQueryUtilTest {

  @Test
  void getQueryWithoutFilter_WithoutSortShouldHaveNoIndex() {

    final QueryRequest queryRequest = ProductQueryUtil.getQueryWithoutFilter(
      TestConstant.TABLE_NAME);

    assertNull(queryRequest.getIndexName());
  }

  @Test
  void getQueryWithoutFilter_WithSortByPriceAscShouldHaveCorrectIndex() {

    final SortReq priceAscSortReq = SortReq.builder()
      .sortBy(DynamoDbConstant.PRICE_ATTR)
      .sortType(DynamoDbConstant.ASC_SORT)
      .build();

    QueryRequest queryRequest = ProductQueryUtil.getQueryWithoutFilter(
      priceAscSortReq, TestConstant.TABLE_NAME);

    assertEquals(DynamoDbConstant.PRICE_SORT_INDEX_NAME, queryRequest.getIndexName());
    assertEquals(true, queryRequest.getScanIndexForward());
  }

  @Test
  void getQueryWithoutFilter_WithSortByPriceDescShouldHaveCorrectIndex() {

    final SortReq priceAscSortReq = SortReq.builder()
      .sortBy(DynamoDbConstant.PRICE_ATTR)
      .sortType(DynamoDbConstant.DESC_SORT)
      .build();

    QueryRequest queryRequest = ProductQueryUtil.getQueryWithoutFilter(
      priceAscSortReq, TestConstant.TABLE_NAME);

    assertEquals(DynamoDbConstant.PRICE_SORT_INDEX_NAME, queryRequest.getIndexName());
    assertEquals(false, queryRequest.getScanIndexForward());
  }

  @Test
  void getQueryWithoutFilter_WithSortByCreatedDateAscShouldHaveCorrectIndex() {

    final SortReq createdDateSortReq = SortReq.builder()
      .sortBy(DynamoDbConstant.CREATED_DATE_ATTR)
      .sortType(DynamoDbConstant.ASC_SORT)
      .build();

    QueryRequest queryRequest = ProductQueryUtil.getQueryWithoutFilter(
      createdDateSortReq, TestConstant.TABLE_NAME);

    assertEquals(DynamoDbConstant.CREATED_DATE_SORT_INDEX_NAME, queryRequest.getIndexName());
    assertEquals(true, queryRequest.getScanIndexForward());
  }

  @Test
  void getQueryWithoutFilter_WithSortByCreatedDateDescShouldHaveCorrectIndex() {

    final SortReq createdDateSortReq = SortReq.builder()
      .sortBy(DynamoDbConstant.CREATED_DATE_ATTR)
      .sortType(DynamoDbConstant.DESC_SORT)
      .build();

    QueryRequest queryRequest = ProductQueryUtil.getQueryWithoutFilter(
      createdDateSortReq, TestConstant.TABLE_NAME);

    assertEquals(DynamoDbConstant.CREATED_DATE_SORT_INDEX_NAME, queryRequest.getIndexName());
    assertEquals(false, queryRequest.getScanIndexForward());
  }

  @Test
  void getQueryByCategory_SortByPriceAscShouldUseCorrectIndex() {

    final SortReq priceAscSortReq = SortReq.builder()
      .sortBy(DynamoDbConstant.PRICE_ATTR)
      .sortType(DynamoDbConstant.ASC_SORT)
      .build();

    QueryRequest queryRequest = ProductQueryUtil.getQueryByCategory(
      "Handphone", priceAscSortReq, TestConstant.TABLE_NAME);
    assertEquals(DynamoDbConstant.PRICE_INDEX_NAME, queryRequest.getIndexName());
    assertEquals(true, queryRequest.getScanIndexForward());
  }

  @Test
  void getQueryByCategory_SortByPriceDescShouldUseCorrectIndex() {

    final SortReq priceAscSortReq = SortReq.builder()
      .sortBy(DynamoDbConstant.PRICE_ATTR)
      .sortType(DynamoDbConstant.DESC_SORT)
      .build();

    QueryRequest queryRequest = ProductQueryUtil.getQueryByCategory(
      "Handphone", priceAscSortReq, TestConstant.TABLE_NAME);
    assertEquals(DynamoDbConstant.PRICE_INDEX_NAME, queryRequest.getIndexName());
    assertEquals(false, queryRequest.getScanIndexForward());
  }

  @Test
  void getQueryByCategory_SortByCreatedDateAscShouldUseCorrectIndex() {

    final SortReq createdDateAscSortReq = SortReq.builder()
      .sortBy(DynamoDbConstant.CREATED_DATE_ATTR)
      .sortType(DynamoDbConstant.ASC_SORT)
      .build();

    QueryRequest queryRequest = ProductQueryUtil.getQueryByCategory(
      "Handphone", createdDateAscSortReq, TestConstant.TABLE_NAME);
    assertEquals(DynamoDbConstant.CREATED_DATE_INDEX_NAME, queryRequest.getIndexName());
    assertEquals(true, queryRequest.getScanIndexForward());
  }

  @Test
  void getQueryByCategory_SortByCreatedDateDescShouldUseCorrectIndex() {

    final SortReq createdDateDescSortReq = SortReq.builder()
      .sortBy(DynamoDbConstant.CREATED_DATE_ATTR)
      .sortType(DynamoDbConstant.DESC_SORT)
      .build();

    QueryRequest queryRequest = ProductQueryUtil.getQueryByCategory(
      "Handphone", createdDateDescSortReq, TestConstant.TABLE_NAME);
    assertEquals(DynamoDbConstant.CREATED_DATE_INDEX_NAME, queryRequest.getIndexName());
    assertEquals(false, queryRequest.getScanIndexForward());
  }

  @Test
  void getQueryByCategory_DefaultSortShouldBeCreatedDateDesc() {

    QueryRequest queryRequest = ProductQueryUtil.getQueryByCategory("Handphone", TestConstant.TABLE_NAME);
    assertEquals(DynamoDbConstant.CREATED_DATE_INDEX_NAME, queryRequest.getIndexName());
    assertEquals(false, queryRequest.getScanIndexForward());
  }
}