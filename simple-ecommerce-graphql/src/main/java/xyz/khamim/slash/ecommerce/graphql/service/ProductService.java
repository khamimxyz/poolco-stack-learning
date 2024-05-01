package xyz.khamim.slash.ecommerce.graphql.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.graphql.model.Product;
import xyz.khamim.slash.ecommerce.graphql.payload.ProductReq;
import xyz.khamim.slash.ecommerce.graphql.repository.ProductRepository;
import xyz.khamim.slash.ecommerce.graphql.service.helper.PayloadToModelConverter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    private final PayloadToModelConverter converter;

    public Mono<Product> createProduct(ProductReq productReq) {

        Product product = converter.fromProductReq(productReq);

        return repository.create(product);
    }

    public Mono<Product> updateProduct(String id, ProductReq productReq) {

        Product product = converter.fromProductReq(productReq);
        product.setId(id);

        return repository.update(product);
    }

    public void deleteProduct(String id) {

        repository.delete(id);
    }

    public Mono<Product> getProduct(String id) {

        return repository.get(id);
    }

    public Mono<List<Product>> getAllProducts() {

        return repository.getAll();
    }
}
