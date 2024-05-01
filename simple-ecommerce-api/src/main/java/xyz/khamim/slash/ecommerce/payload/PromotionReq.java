package xyz.khamim.slash.ecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PromotionReq {
    private String name;
    private Integer discount;
    private List<String> productIds;
}