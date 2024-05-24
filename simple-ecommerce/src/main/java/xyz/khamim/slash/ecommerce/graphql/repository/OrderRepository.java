package xyz.khamim.slash.ecommerce.graphql.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.springframework.stereotype.Repository;
import xyz.khamim.slash.ecommerce.graphql.model.order.Order;
import xyz.khamim.slash.ecommerce.graphql.util.DynamoDbTable;

@Repository
public class OrderRepository extends DynamoDbRepository<Order> {
  public OrderRepository(final AmazonDynamoDB amazonDynamoDB, final DynamoDbTable table) {
    super(amazonDynamoDB, table);
  }
}
