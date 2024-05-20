package xyz.khamim.slash.ecommerce.graphql.model.order;

import lombok.*;
import xyz.khamim.slash.ecommerce.graphql.model.DynamoItem;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order extends DynamoItem {

    private OrderData orderData;
    private String createdDate;
    private String createdBy;
}
