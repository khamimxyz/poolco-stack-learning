package xyz.khamim.slash.ecommerce.graphql.service.helper;

import org.springframework.stereotype.Component;
import xyz.khamim.slash.ecommerce.graphql.input.ProductReq;
import xyz.khamim.slash.ecommerce.graphql.input.ReviewReq;
import xyz.khamim.slash.ecommerce.graphql.model.Product;
import xyz.khamim.slash.ecommerce.graphql.model.Review;

@Component
public class PayloadToModelConverter {

    public Product fromProductReq(ProductReq productReq, String createdDate) {

        return Product.builder()
                .name(productReq.getName())
                .category(productReq.getCategory())
                .price(productReq.getPrice())
                .createdDate(createdDate)
                .build();
    }

    public Review fromReviewReq(ReviewReq reviewReq) {

        return Review.builder()
                .productId(reviewReq.getProductId())
                .star(reviewReq.getStar())
                .reviewerName(reviewReq.getReviewerName())
                .build();
    }
}
