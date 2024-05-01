package xyz.khamim.slash.ecommerce.cucumber.payload;

import lombok.Data;

import java.util.List;

@Data
public class PromotionResponse {
    private String id;
    private String name;
    private Integer discount;
    private List<String> productIds;
}
