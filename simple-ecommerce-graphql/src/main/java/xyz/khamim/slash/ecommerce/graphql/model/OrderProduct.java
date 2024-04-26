package xyz.khamim.slash.ecommerce.graphql.model;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct extends DynamoItem {
    private String productId;
    private Integer qty;
    private Integer price;
    private String promotionId;
}
