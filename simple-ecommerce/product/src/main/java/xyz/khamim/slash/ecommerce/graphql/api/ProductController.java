package xyz.khamim.slash.ecommerce.graphql.api;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.graphql.dto.ProductWithReviewDto;
import xyz.khamim.slash.ecommerce.graphql.input.FilterProductReq;
import xyz.khamim.slash.ecommerce.graphql.input.ProductReq;
import xyz.khamim.slash.ecommerce.graphql.input.ReviewReq;
import xyz.khamim.slash.ecommerce.graphql.model.Product;
import xyz.khamim.slash.ecommerce.graphql.model.Review;
import xyz.khamim.slash.ecommerce.graphql.security.SecureMethod;
import xyz.khamim.slash.ecommerce.graphql.service.FeedService;
import xyz.khamim.slash.ecommerce.graphql.service.ProductService;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    private final FeedService feedService;

    @DgsQuery
    public Mono<Product> getProduct(@InputArgument final String id) {

        return service.getProduct(id);
    }

    @DgsQuery
    public Mono<ProductWithReviewDto> getProductWithReviews(@InputArgument final String id) {

        return service.getProductWithReviews(id);
    }
    @DgsQuery
    public Mono<List<Product>> getAllProducts(@InputArgument final FilterProductReq req) {

        return service.getAllProducts(req);
    }

    @DgsMutation
    @SecureMethod(module = "product")
    public Mono<Product> addProduct(final DataFetchingEnvironment env, @InputArgument final ProductReq productReq) {

        return service.createProduct(productReq)
          .map(product -> {
              feedService.sendProductFeed(product);
              return product;
          });
    }

    @DgsMutation
    @SecureMethod(module = "review")
    public Mono<Review> addReview(final DataFetchingEnvironment env, @InputArgument final ReviewReq reviewReq) {

        return service.createReview(reviewReq)
          .flatMap(review -> {
              if (review.getStar() == 5) {
                  return getProduct(review.getProductId())
                    .doOnNext(feedService::sendRecommendationFeed)
                    .thenReturn(review);
              }

              return Mono.just(review);
          });
    }
}
