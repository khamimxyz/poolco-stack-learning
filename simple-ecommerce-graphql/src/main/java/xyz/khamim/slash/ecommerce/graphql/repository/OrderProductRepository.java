package xyz.khamim.slash.ecommerce.graphql.repository;

import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.graphql.model.OrderProduct;
import xyz.khamim.slash.ecommerce.graphql.repository.util.ModelMapper;

import java.util.List;

@Repository
public class OrderProductRepository extends DynamoDbRepository<OrderProduct> {

    public Mono<List<OrderProduct>> createOrderProducts(List<OrderProduct> items) {

        return Mono.fromRunnable(() -> items.forEach(item -> {
            PutItemRequest putItemRequest = new PutItemRequest()
                    .withTableName(getTableName())
                    .withItem(ModelMapper.mapToAttributes(item));
            amazonDynamoDB.putItem(putItemRequest);
        })).thenReturn(items);
    }
}
