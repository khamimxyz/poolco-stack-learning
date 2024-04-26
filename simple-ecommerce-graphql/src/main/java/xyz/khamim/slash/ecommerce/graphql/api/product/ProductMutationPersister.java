package xyz.khamim.slash.ecommerce.graphql.api.product;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.graphql.model.Product;
import xyz.khamim.slash.ecommerce.graphql.payload.ProductReq;
import xyz.khamim.slash.ecommerce.graphql.service.ProductService;

@DgsComponent
@RequiredArgsConstructor
public class ProductMutationPersister {

    private final ProductService service;

    @DgsMutation
    public Mono<Product> addProduct(ProductReq productReq) {

        return service.createProduct(productReq);
    }

    @DgsMutation
    public Mono<Product> updateProduct(String id, ProductReq productReq) {

        return service.updateProduct(id, productReq);
    }

    @DgsMutation
    public Mono<Boolean> deleteProduct(String id) {

        service.deleteProduct(id);

        return Mono.just(true);
    }
}
