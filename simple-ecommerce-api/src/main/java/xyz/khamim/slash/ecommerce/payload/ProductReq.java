package xyz.khamim.slash.ecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductReq {

    private String id;
    private String name;
    private String category;
    private Integer price;
}
