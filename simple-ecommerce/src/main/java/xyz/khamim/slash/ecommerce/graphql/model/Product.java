package xyz.khamim.slash.ecommerce.graphql.model;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product extends DynamoItem {

    private String name;
    private String category;
    private Integer price;
    private String createdDate;
}
