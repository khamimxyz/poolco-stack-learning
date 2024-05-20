package xyz.khamim.slash.ecommerce.graphql.api;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.graphql.dto.ProductWithReviewDto;
import xyz.khamim.slash.ecommerce.graphql.input.FilterProductReq;
import xyz.khamim.slash.ecommerce.graphql.input.ReviewReq;
import xyz.khamim.slash.ecommerce.graphql.model.Product;
import xyz.khamim.slash.ecommerce.graphql.input.ProductReq;
import xyz.khamim.slash.ecommerce.graphql.model.Review;
import xyz.khamim.slash.ecommerce.graphql.service.ProductService;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @DgsQuery
    public Mono<Product> getProduct(String id) {

        return service.getProduct(id);
    }

    @DgsQuery
    public Mono<ProductWithReviewDto> getProductWithReviews(String id) {

        return service.getProductWithReviews(id);
    }
    @DgsQuery
    public Mono<List<Product>> getAllProducts(@InputArgument FilterProductReq req) {

        return service.getAllProducts(req);
    }

    @DgsMutation
    public Mono<Product> addProduct(ProductReq productReq) {

        return service.createProduct(productReq);
    }

    @DgsMutation
    public Mono<Review> addReview(ReviewReq reviewReq) {

        return service.createReview(reviewReq);
    }

    @DgsMutation
    public Mono<Product> updateProduct(String id, ProductReq productReq) {

        return service.updateProduct(id, productReq);
    }
}
