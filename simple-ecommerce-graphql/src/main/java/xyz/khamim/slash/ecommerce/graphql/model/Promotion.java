package xyz.khamim.slash.ecommerce.graphql.model;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Promotion extends DynamoItem {

    private String name;
    private Integer discount;
    private List<String> productIds;
}
