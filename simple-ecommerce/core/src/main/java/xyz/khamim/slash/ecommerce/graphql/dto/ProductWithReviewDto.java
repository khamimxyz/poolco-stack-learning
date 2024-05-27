package xyz.khamim.slash.ecommerce.graphql.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.khamim.slash.ecommerce.graphql.model.Product;
import xyz.khamim.slash.ecommerce.graphql.model.Review;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductWithReviewDto {

    private Product product;
    private List<Review> reviews;
}
