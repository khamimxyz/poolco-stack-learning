package xyz.khamim.slash.ecommerce.graphql.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.springframework.stereotype.Repository;
import xyz.khamim.slash.ecommerce.graphql.model.Review;

@Repository
public class ReviewRepository extends DynamoDbRepository<Review>{
  public ReviewRepository(AmazonDynamoDB amazonDynamoDB) {
    super(amazonDynamoDB);
  }
}
