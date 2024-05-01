package xyz.khamim.slash.ecommerce.service;

import com.netflix.graphql.dgs.client.WebClientGraphQLClient;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.payload.OrderReq;
import xyz.khamim.slash.ecommerce.service.helper.GraphQLQueryBuilder;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderService {
    private final WebClientGraphQLClient client;

    public Mono<?> createOrder(OrderReq order) {

        @Language("graphql") String mutation = new GraphQLQueryBuilder()
                .mutation()
                .operation("addOrder", Map.of("orderReq", order))
                .fields("id")
                .build();

        return client
                .reactiveExecuteQuery(mutation)
                .map(response -> response.getData().get("addOrder"));
    }

    public Mono<?> getOrderDetail(String id) {

        @Language("graphql") String query = new GraphQLQueryBuilder()
                .query()
                .operation("getOrderDetail", Map.of("id", id))
                .fields("id", Map.of(
                        "orderProducts",
                        List.of("productName", "qty", "price", "promotionName", "discount")
                ))
                .build();

        return client
                .reactiveExecuteQuery(query)
                .map(response -> response.getData().get("getOrderDetail"));
    }
}
