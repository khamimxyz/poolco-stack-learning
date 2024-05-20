package xyz.khamim.slash.ecommerce.graphql.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductReq {
    private String name;
    private String category;
    private Integer price;
}
