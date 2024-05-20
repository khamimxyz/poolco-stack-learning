package xyz.khamim.slash.ecommerce.graphql.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.springframework.stereotype.Repository;
import xyz.khamim.slash.ecommerce.graphql.model.order.Order;

@Repository
public class OrderRepository extends DynamoDbRepository<Order> {
  public OrderRepository(AmazonDynamoDB amazonDynamoDB) {
    super(amazonDynamoDB);
  }
}
