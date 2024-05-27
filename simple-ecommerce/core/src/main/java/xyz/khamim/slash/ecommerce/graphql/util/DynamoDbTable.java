package xyz.khamim.slash.ecommerce.graphql.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DynamoDbTable {

  private String tableName;
}
