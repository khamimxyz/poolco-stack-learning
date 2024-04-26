package xyz.khamim.slash.ecommerce.graphql.payload;

import lombok.Data;

import java.util.List;

@Data
public class OrderReq {
    private List<OrderProductReq> orderProducts;
}
