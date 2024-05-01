package xyz.khamim.slash.ecommerce.graphql.api.product;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.graphql.model.Product;
import xyz.khamim.slash.ecommerce.graphql.service.ProductService;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class ProductQueryFetcher {

    private final ProductService service;

    @DgsQuery
    public Mono<Product> getProduct(String id) {

        return service.getProduct(id);
    }

    @DgsQuery
    public Mono<List<Product>> getAllProducts() {

        return service.getAllProducts();
    }
}
