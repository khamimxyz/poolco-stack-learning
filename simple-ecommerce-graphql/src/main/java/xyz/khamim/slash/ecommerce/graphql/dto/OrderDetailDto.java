package xyz.khamim.slash.ecommerce.graphql.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OrderDetailDto {
    private String id;
    private List<OrderProductDetailDto> orderProducts;
}
