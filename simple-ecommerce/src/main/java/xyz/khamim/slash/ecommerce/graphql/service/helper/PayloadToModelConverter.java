package xyz.khamim.slash.ecommerce.graphql.service.helper;

import org.springframework.stereotype.Component;
import xyz.khamim.slash.ecommerce.graphql.input.ReviewReq;
import xyz.khamim.slash.ecommerce.graphql.model.order.OrderProduct;
import xyz.khamim.slash.ecommerce.graphql.model.Product;
import xyz.khamim.slash.ecommerce.graphql.input.OrderProductReq;
import xyz.khamim.slash.ecommerce.graphql.input.ProductReq;
import xyz.khamim.slash.ecommerce.graphql.model.Review;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<OrderProduct> fromOrderProductReq(List<OrderProductReq> list) {

        final List<OrderProduct> orderProducts = new ArrayList<>();
        list.forEach(e -> orderProducts.add(OrderProduct.builder()
                .productId(e.getProductId())
                .productName(e.getProductName())
                .qty(e.getQty())
                .price(e.getPrice())
                .build())
        );

        return orderProducts;
    }

    public Review fromReviewReq(ReviewReq reviewReq) {

        return Review.builder()
                .productId(reviewReq.getProductId())
                .star(reviewReq.getStar())
                .reviewerName(reviewReq.getReviewerName())
                .build();
    }
}
