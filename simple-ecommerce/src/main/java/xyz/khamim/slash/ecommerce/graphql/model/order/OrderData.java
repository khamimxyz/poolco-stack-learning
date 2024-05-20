package xyz.khamim.slash.ecommerce.graphql.model.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderData {

    private List<OrderProduct> orderProducts;
}
