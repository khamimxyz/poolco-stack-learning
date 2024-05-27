package xyz.khamim.slash.ecommerce.graphql.boot;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.khamim.slash.ecommerce.graphql.constant.DynamoDbConstant;
import xyz.khamim.slash.ecommerce.graphql.util.DynamoDbTable;

@Component
@Slf4j
@RequiredArgsConstructor
public class DynamoDbTableInitializer {

    private final AmazonDynamoDB amazonDynamoDB;

    private final DynamoDbTable dynamoDbTable;

    @PostConstruct
    public void initTable() {
        final String tableName = dynamoDbTable.getTableName();

        try {
            amazonDynamoDB.describeTable(tableName);
        } catch (ResourceNotFoundException e) {

            CreateTableRequest createTableRequest = new CreateTableRequest()
              .withTableName(tableName)
              .withAttributeDefinitions(
                new AttributeDefinition("pk", ScalarAttributeType.S),
                new AttributeDefinition("sk", ScalarAttributeType.S),
                new AttributeDefinition("category", ScalarAttributeType.S),
                new AttributeDefinition("price", ScalarAttributeType.N),
                new AttributeDefinition("createdDate", ScalarAttributeType.S)
              )
              .withKeySchema(
                new KeySchemaElement("pk", KeyType.HASH),
                new KeySchemaElement("sk", KeyType.RANGE)
              )
              .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L));

            GlobalSecondaryIndex priceIndex = new GlobalSecondaryIndex()
              .withIndexName(DynamoDbConstant.PRICE_INDEX_NAME)
              .withKeySchema(
                new KeySchemaElement("category", KeyType.HASH),
                new KeySchemaElement("price", KeyType.RANGE)
              )
              .withProjection(new Projection().withProjectionType(ProjectionType.ALL))
              .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L));

            GlobalSecondaryIndex createdDateIndex = new GlobalSecondaryIndex()
              .withIndexName(DynamoDbConstant.CREATED_DATE_INDEX_NAME)
              .withKeySchema(
                new KeySchemaElement("category", KeyType.HASH),
                new KeySchemaElement("createdDate", KeyType.RANGE)
              )
              .withProjection(new Projection().withProjectionType(ProjectionType.ALL))
              .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L));


            LocalSecondaryIndex priceSortIndex = new LocalSecondaryIndex()
              .withIndexName(DynamoDbConstant.PRICE_SORT_INDEX_NAME)
              .withKeySchema(
                new KeySchemaElement("pk", KeyType.HASH),
                new KeySchemaElement("price", KeyType.RANGE)
              )
              .withProjection(new Projection().withProjectionType(ProjectionType.ALL));

            LocalSecondaryIndex createdDateSortIndex = new LocalSecondaryIndex()
              .withIndexName(DynamoDbConstant.CREATED_DATE_SORT_INDEX_NAME)
              .withKeySchema(
                new KeySchemaElement("pk", KeyType.HASH),
                new KeySchemaElement("createdDate", KeyType.RANGE)
              )
              .withProjection(new Projection().withProjectionType(ProjectionType.ALL));

            createTableRequest.withGlobalSecondaryIndexes(priceIndex, createdDateIndex);
            createTableRequest.withLocalSecondaryIndexes(priceSortIndex, createdDateSortIndex);

            log.info("Creating dynamo db table");
            amazonDynamoDB.createTable(createTableRequest);
        }
    }
}
