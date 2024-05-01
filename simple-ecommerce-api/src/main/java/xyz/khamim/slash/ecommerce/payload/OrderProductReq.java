package xyz.khamim.slash.ecommerce.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderProductReq {
    private String productId;
    private Integer qty;
    private Integer price;
    private String promotionId;
}