package xyz.khamim.slash.ecommerce.graphql.model.order;

import lombok.*;

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
