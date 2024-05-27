package xyz.khamim.slash.ecommerce.graphql.service.helper;

import org.springframework.stereotype.Component;
import xyz.khamim.slash.ecommerce.graphql.model.OrderProduct;
import xyz.khamim.slash.ecommerce.graphql.input.OrderProductReq;

import java.util.ArrayList;
import java.util.List;

@Component
public class PayloadToModelConverter {

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
}
