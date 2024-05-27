package xyz.khamim.slash.ecommerce.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {
    private String productId;
    private String productName;
    private Integer qty;
    private Integer price;
}
