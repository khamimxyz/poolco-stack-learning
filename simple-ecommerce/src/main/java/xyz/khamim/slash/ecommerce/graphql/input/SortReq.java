package xyz.khamim.slash.ecommerce.graphql.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SortReq {
  private String sortBy;
  private String sortType;
}
