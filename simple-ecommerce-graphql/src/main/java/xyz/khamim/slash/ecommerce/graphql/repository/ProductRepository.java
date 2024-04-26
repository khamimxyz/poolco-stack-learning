package xyz.khamim.slash.ecommerce.graphql.repository;

import org.springframework.stereotype.Repository;
import xyz.khamim.slash.ecommerce.graphql.model.Product;

@Repository
public class ProductRepository extends DynamoDbRepository<Product> {}
