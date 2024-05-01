package xyz.khamim.slash.ecommerce.payload;

import lombok.Data;

import java.util.List;

@Data
public class OrderReq {
    private List<OrderProductReq> orderProducts;
}