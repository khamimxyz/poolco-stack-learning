package xyz.khamim.slash.ecommerce.cucumber.payload;

import lombok.Data;

@Data
public class ProductResponse {

    private String id;
    private String name;
    private String category;
    private Integer price;
}
