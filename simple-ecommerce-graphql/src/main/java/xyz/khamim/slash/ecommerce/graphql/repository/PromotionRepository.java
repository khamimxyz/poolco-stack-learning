package xyz.khamim.slash.ecommerce.graphql.repository;

import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.graphql.model.Promotion;
import xyz.khamim.slash.ecommerce.graphql.repository.util.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PromotionRepository extends DynamoDbRepository<Promotion> {

    private static final String INDEX = "ProductIdIndex";

    public Mono<List<Promotion>> getPromotionsByProductId(String productId) {

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(getTableName());

        return Mono
                .fromCallable(() -> dynamoDB.getTable(getTableName()))
                .flatMap(table -> {
                    final List<Promotion> promotions = new ArrayList<>();
                    table.scan().forEach(item -> {
                        if (item.getStringSet("productIds").contains(productId)) {
                           promotions.add(ModelMapper.mapObjectToModel(item.asMap(), getTableClass()));
                        }
                    });

                    return Mono.just(promotions);
                });
    }
}
