package xyz.khamim.slash.ecommerce.graphql.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductReq {
    private String productId;
    private String productName;
    private Integer qty;
    private Integer price;
}
