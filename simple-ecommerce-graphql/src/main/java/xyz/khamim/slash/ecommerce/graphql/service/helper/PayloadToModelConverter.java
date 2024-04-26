package xyz.khamim.slash.ecommerce.graphql.service.helper;

import org.springframework.stereotype.Component;
import xyz.khamim.slash.ecommerce.graphql.model.OrderProduct;
import xyz.khamim.slash.ecommerce.graphql.model.Product;
import xyz.khamim.slash.ecommerce.graphql.model.Promotion;
import xyz.khamim.slash.ecommerce.graphql.payload.OrderProductReq;
import xyz.khamim.slash.ecommerce.graphql.payload.OrderReq;
import xyz.khamim.slash.ecommerce.graphql.payload.ProductReq;
import xyz.khamim.slash.ecommerce.graphql.payload.PromotionReq;

import java.util.ArrayList;
import java.util.List;

@Component
public class PayloadToModelConverter {

    public Product fromProductReq(ProductReq productReq) {

        return Product.builder()
                .name(productReq.getName())
                .category(productReq.getCategory())
                .price(productReq.getPrice())
                .build();
    }

    public Promotion fromPromotionReq(PromotionReq promotionReq) {

        return Promotion.builder()
                .name(promotionReq.getName())
                .discount(promotionReq.getDiscount())
                .productIds(promotionReq.getProductIds())
                .build();
    }

    public List<OrderProduct> fromOrderProductReq(List<OrderProductReq> list) {

        final List<OrderProduct> orderProducts = new ArrayList<>();
        list.forEach(e -> orderProducts.add(OrderProduct.builder()
                .productId(e.getProductId())
                .promotionId(e.getPromotionId())
                .qty(e.getQty())
                .price(e.getPrice())
                .build())
        );

        return orderProducts;
    }
}
