package xyz.khamim.slash.ecommerce.service;

import com.netflix.graphql.dgs.client.WebClientGraphQLClient;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.payload.PromotionReq;
import xyz.khamim.slash.ecommerce.service.helper.GraphQLQueryBuilder;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final WebClientGraphQLClient client;

    public Mono<?> createPromotion(PromotionReq promotion) {

        @Language("graphql") String mutation = new GraphQLQueryBuilder()
                .mutation()
                .operation("addPromotion", Map.of("promotionReq", promotion))
                .fields("id")
                .build();

        return client
                .reactiveExecuteQuery(mutation)
                .map(response -> response.getData().get("addPromotion"));
    }

    public Mono<?> getPromotion(String id) {

        @Language("graphql") String query = new GraphQLQueryBuilder()
                .query()
                .operation("getPromotion", Map.of("id", id))
                .fields("name", "discount")
                .build();

        return client
                .reactiveExecuteQuery(query)
                .map(response -> response.getData().get("getPromotion"));
    }

    public Mono<?> getAllPromotions() {

        @Language("graphql") String query = new GraphQLQueryBuilder()
                .query()
                .operation("getAllPromotions", Map.of())
                .fields("id", "name", "discount", "productIds")
                .build();

        return client
                .reactiveExecuteQuery(query)
                .map(response -> response.getData().get("getAllPromotions"));
    }

    public Mono<?> updatePromotion(String id, PromotionReq promotion) {

        @Language("graphql") String mutation = new GraphQLQueryBuilder()
                .mutation()
                .operation("updatePromotion", Map.of("id", id, "promotionReq", promotion))
                .fields("id")
                .build();

        return client
                .reactiveExecuteQuery(mutation)
                .map(response -> response.getData().get("updatePromotion"));
    }
}
