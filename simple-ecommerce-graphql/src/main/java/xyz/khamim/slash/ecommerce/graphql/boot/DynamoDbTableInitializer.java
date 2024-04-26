package xyz.khamim.slash.ecommerce.graphql.boot;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.khamim.slash.ecommerce.graphql.dynamodb.AmazonDynamoDBFactory;

@Component
@Slf4j
public class DynamoDbTableInitializer {

    private final AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBFactory.getAmazonDynamoDB();

    @PostConstruct
    public void initTable() {

        log.info("Creating dynamo db table");
        amazonDynamoDB.createTable(createTable("product"));
        amazonDynamoDB.createTable(createTable("promotion"));
        amazonDynamoDB.createTable(createTable("order"));
        amazonDynamoDB.createTable(createTable("orderproduct"));
    }

    private CreateTableRequest createTable(final String tableName) {
        return new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(new KeySchemaElement("id", KeyType.HASH))
                .withAttributeDefinitions(new AttributeDefinition("id", ScalarAttributeType.S))
                .withProvisionedThroughput(new ProvisionedThroughput(5L, 5L));
    }

}
