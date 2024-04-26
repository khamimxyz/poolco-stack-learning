package xyz.khamim.slash.ecommerce.graphql.payload;

import lombok.Data;

@Data
public class OrderProductReq {
    private String productId;
    private Integer qty;
    private Integer price;
    private String promotionId;
}
