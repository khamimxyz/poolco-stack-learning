package xyz.khamim.slash.ecommerce.graphql.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class DynamoItem {
    private String id = UUID.randomUUID().toString();
}
