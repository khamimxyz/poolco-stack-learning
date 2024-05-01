package xyz.khamim.slash.ecommerce.service;

import com.netflix.graphql.dgs.client.WebClientGraphQLClient;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.payload.ProductReq;
import xyz.khamim.slash.ecommerce.service.helper.GraphQLQueryBuilder;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProductService {

    private final WebClientGraphQLClient client;

    public Mono<?> createProduct(ProductReq product) {

        @Language("graphql") String mutation = new GraphQLQueryBuilder()
                .mutation()
                .operation("addProduct", Map.of("productReq", product))
                .fields("id")
                .build();

        return client
                .reactiveExecuteQuery(mutation)
                .map(response -> response.getData().get("addProduct"));
    }

    public Mono<?> getProduct(String id) {

        @Language("graphql") String query = new GraphQLQueryBuilder()
                .query()
                .operation("getProduct", Map.of("id", id))
                .fields("name", "category", "price")
                .build();

        return client
                .reactiveExecuteQuery(query)
                .map(response -> response.getData().get("getProduct"));
    }

    public Mono<?> getAllProducts() {

        @Language("graphql") String query = new GraphQLQueryBuilder()
                .query()
                .operation("getAllProducts", Map.of())
                .fields("id", "name", "category", "price")
                .build();

        return client
                .reactiveExecuteQuery(query)
                .map(response -> response.getData().get("getAllProducts"));
    }

    public Mono<?> updateProduct(String id, ProductReq product) {

        @Language("graphql") String mutation = new GraphQLQueryBuilder()
                .mutation()
                .operation("updateProduct", Map.of("id", id, "productReq", product))
                .fields("id")
                .build();

        return client
                .reactiveExecuteQuery(mutation)
                .map(response -> response.getData().get("updateProduct"));
    }
}
