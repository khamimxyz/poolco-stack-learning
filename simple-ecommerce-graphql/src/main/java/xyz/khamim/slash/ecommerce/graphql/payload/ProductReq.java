package xyz.khamim.slash.ecommerce.graphql.payload;

import lombok.Data;

@Data
public class ProductReq {
    private String name;
    private String category;
    private Integer price;
}
