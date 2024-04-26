package xyz.khamim.slash.ecommerce.graphql.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OrderProductDetailDto {
    private String productName;
    private Integer qty;
    private Integer price;
    private String promotionName;
    private Integer discount;
}
