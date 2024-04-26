package xyz.khamim.slash.ecommerce.graphql.model;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order extends DynamoItem {

    private List<String> orderProductIds;
}
