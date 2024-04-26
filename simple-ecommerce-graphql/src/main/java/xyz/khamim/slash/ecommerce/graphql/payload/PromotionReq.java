package xyz.khamim.slash.ecommerce.graphql.payload;

import lombok.Data;

import java.util.List;

@Data
public class PromotionReq {
    private String name;
    private Integer discount;
    private List<String> productIds;
}
