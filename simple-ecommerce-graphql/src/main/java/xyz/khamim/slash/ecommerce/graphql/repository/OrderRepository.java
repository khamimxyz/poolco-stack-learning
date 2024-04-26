package xyz.khamim.slash.ecommerce.graphql.repository;

import org.springframework.stereotype.Repository;
import xyz.khamim.slash.ecommerce.graphql.model.Order;

@Repository
public class OrderRepository extends DynamoDbRepository<Order> {
}
