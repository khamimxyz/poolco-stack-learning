package xyz.khamim.slash.ecommerce.graphql.constant;

public interface DynamoDbConstant {

  String PRICE_INDEX_NAME = "PriceIndex";
  String CREATED_DATE_INDEX_NAME = "CreatedDateIndex";
  String PRICE_SORT_INDEX_NAME = "PriceSortIndex";
  String CREATED_DATE_SORT_INDEX_NAME = "CreatedDateSortIndex";
  String PRICE_ATTR = "price";
  String CREATED_DATE_ATTR = "createdDate";
  String ASC_SORT = "ASC";
  String DESC_SORT = "DESC";
}
