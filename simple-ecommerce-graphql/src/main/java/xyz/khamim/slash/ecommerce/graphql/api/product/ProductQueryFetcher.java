package xyz.khamim.slash.ecommerce.graphql.api.product;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.graphql.model.Product;
import xyz.khamim.slash.ecommerce.graphql.service.ProductService;

@DgsComponent
@RequiredArgsConstructor
public class ProductQueryFetcher {

    private final ProductService service;

    @DgsQuery
    public Mono<Product> getProduct(String id) {

        return service.getProduct(id);
    }
}
