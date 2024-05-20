package xyz.khamim.slash.ecommerce.graphql.model;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review extends DynamoItem {

    private String productId;
    private Integer star;
    private String reviewerName;
}
