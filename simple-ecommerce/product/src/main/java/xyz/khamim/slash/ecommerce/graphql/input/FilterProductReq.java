package xyz.khamim.slash.ecommerce.graphql.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterProductReq {

  private String category;

  private List<SortReq> sortReqList;
}
