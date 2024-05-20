package xyz.khamim.slash.ecommerce.graphql.model;

import lombok.Data;

import java.util.UUID;

@Data
public class DynamoItem {
    private String id = UUID.randomUUID().toString().replaceAll("-", "");
    private String pk;
    private String sk;

    public DynamoItem() {
        pk = getClass().getSimpleName().toLowerCase();
        sk = id;
    }
}
