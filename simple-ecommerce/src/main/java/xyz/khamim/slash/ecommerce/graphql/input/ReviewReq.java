package xyz.khamim.slash.ecommerce.graphql.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReq {

    private String productId;
    private Integer star;
    private String reviewerName;
}
