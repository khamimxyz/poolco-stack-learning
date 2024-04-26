package xyz.khamim.slash.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class Product {

    private String id;
    private String name;
    private String category;
    private Integer price;
}
