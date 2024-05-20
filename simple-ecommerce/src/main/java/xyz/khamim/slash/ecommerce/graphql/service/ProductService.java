package xyz.khamim.slash.ecommerce.graphql.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.graphql.constant.DynamoDbConstant;
import xyz.khamim.slash.ecommerce.graphql.dto.ProductWithReviewDto;
import xyz.khamim.slash.ecommerce.graphql.input.FilterProductReq;
import xyz.khamim.slash.ecommerce.graphql.input.ReviewReq;
import xyz.khamim.slash.ecommerce.graphql.input.SortReq;
import xyz.khamim.slash.ecommerce.graphql.model.Product;
import xyz.khamim.slash.ecommerce.graphql.input.ProductReq;
import xyz.khamim.slash.ecommerce.graphql.model.Review;
import xyz.khamim.slash.ecommerce.graphql.repository.ProductRepository;
import xyz.khamim.slash.ecommerce.graphql.repository.ReviewRepository;
import xyz.khamim.slash.ecommerce.graphql.service.helper.PayloadToModelConverter;
import xyz.khamim.slash.ecommerce.graphql.service.helper.ProductComparator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    private final ReviewRepository reviewRepository;

    private final PayloadToModelConverter converter;

    public Mono<Product> createProduct(ProductReq productReq) {

      String createdDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
      Product product = converter.fromProductReq(productReq, createdDate);

      return Mono.fromCallable(() -> repository.create(product));
    }

    public Mono<Review> createReview(ReviewReq reviewReq) {

      Review review = converter.fromReviewReq(reviewReq);
      review.setPk("product");
      review.setSk(reviewReq.getProductId()+"#review#"+review.getId());

      return Mono.fromCallable(() -> reviewRepository.create(review));
    }

    public Mono<Product> updateProduct(String id, ProductReq productReq) {

      Product product = converter.fromProductReq(productReq, null);
      product.setId(id);

      return repository.update(product);
    }

    public Mono<Product> getProduct(String id) {
      return Mono.fromCallable(() -> repository.get(id));
    }

    public Mono<List<Product>> getAllProducts(FilterProductReq req) {

        return Mono.fromCallable(() -> repository.getAll(req))
          .map(products -> {
            List<Product> copyOfProducts = new ArrayList<>(products);
            if (req != null && req.getSortReqList() != null
              && req.getSortReqList().size() == 2) {
              final SortReq secondSort = req.getSortReqList().get(1);
              boolean reverse = DynamoDbConstant.DESC_SORT.equals(secondSort.getSortType());

              if (DynamoDbConstant.CREATED_DATE_ATTR.equals(secondSort.getSortBy())) {
                copyOfProducts.sort(ProductComparator.getProductPriceCreatedDateComparator(reverse));
              } else {
                copyOfProducts.sort(ProductComparator.getProductCreatedDatePriceComparator(reverse));
              }
            }

            return copyOfProducts;
          });
    }

    public Mono<ProductWithReviewDto> getProductWithReviews(String id) {

      return Mono.fromCallable(() -> repository.getProductWithReviews(id));
    }
}
