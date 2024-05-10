package xyz.khamim.slash.learn.dynamodb;

import com.amazonaws.services.dynamodbv2.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import xyz.khamim.slash.learn.dynamodb.factory.AmazonDynamoDBFactory;

import java.util.List;
import java.util.Map;

public class FilmographyDynamodbTest {

    private static final String TABLE_NAME = "Filmography";
    private static final String INDEX_NAME = "MovieActorIndex";

    @BeforeAll
    public static void initTable() {
        CreateTableRequest request = new CreateTableRequest()
            .withTableName(TABLE_NAME)
            .withKeySchema(
                    new KeySchemaElement("actor", KeyType.HASH),
                    new KeySchemaElement("movie", KeyType.RANGE)
            )
            .withAttributeDefinitions(
                    new AttributeDefinition("actor", ScalarAttributeType.S),
                    new AttributeDefinition("movie", ScalarAttributeType.S)
            )
            .withProvisionedThroughput(new ProvisionedThroughput(5L, 5L))
            .withGlobalSecondaryIndexes(
                    new GlobalSecondaryIndex()
                            .withIndexName(INDEX_NAME)
                            .withKeySchema(
                                    new KeySchemaElement("movie", KeyType.HASH),
                                    new KeySchemaElement("actor", KeyType.RANGE)
                            )
                            .withProjection(new Projection().withProjectionType(ProjectionType.ALL))
                            .withProvisionedThroughput(new ProvisionedThroughput(5L, 5L))
            );

        AmazonDynamoDBFactory.getAmazonDynamoDB().createTable(request);
        final String[] datas = new String[] {
                "Tom Hanks,Cast Away,Chuck Noland,2000,Drama",
                "Tom Hanks,Toy Story,Woodie,1995,Children",
                "Tim Allen,Toy Story,Buzz Lightyear,1995,Children",
                "Natalie Portman,Black Swan,Nina Sayers,2010,Drama"
        };

        for(String data : datas) {
            final String[] attributes = data.split(",");
            final PutItemRequest putItemRequest = new PutItemRequest()
                    .withTableName(TABLE_NAME)
                    .withItem(Map.of(
                            "actor", new AttributeValue().withS(attributes[0]),
                            "movie", new AttributeValue().withS(attributes[1]),
                            "role", new AttributeValue().withS(attributes[2]),
                            "year", new AttributeValue().withN(attributes[3]),
                            "genre", new AttributeValue().withS(attributes[4])
                    ));
            AmazonDynamoDBFactory.getAmazonDynamoDB().putItem(putItemRequest);
        }
    }

    @Test
    public void testGetItem() {

        GetItemRequest request = new GetItemRequest()
                .withTableName(TABLE_NAME)
                .addKeyEntry("actor", new AttributeValue().withS("Tom Hanks"))
                .addKeyEntry("movie", new AttributeValue().withS("Toy Story"));

        GetItemResult result = AmazonDynamoDBFactory.getAmazonDynamoDB().getItem(request);

        Assertions.assertEquals("Woodie", result.getItem().get("role").getS());
        Assertions.assertEquals("1995", result.getItem().get("year").getN());
        Assertions.assertEquals("Children", result.getItem().get("genre").getS());
    }

    @Test
    public void testQuery() {
        QueryRequest query = new QueryRequest()
                .withTableName(TABLE_NAME)
                .withKeyConditionExpression("#actor = :actorValue")
                .withExpressionAttributeNames(Map.of("#actor", "actor"))
                .withExpressionAttributeValues(Map.of(":actorValue", new AttributeValue().withS("Tom Hanks")));

        QueryResult result = AmazonDynamoDBFactory.getAmazonDynamoDB().query(query);
        List<Map<String, AttributeValue>> rows = result.getItems();
        Assertions.assertEquals(rows.size(), 2);
    }

    @Test
    public void testQueryWithBetween() {
        QueryRequest query = new QueryRequest()
                .withTableName(TABLE_NAME)
                .withKeyConditionExpression("#a = :actor AND #b BETWEEN :r1 AND :r2")
                .withExpressionAttributeNames(Map.of("#a", "actor", "#b", "movie"))
                .withExpressionAttributeValues(
                        Map.of(
                            ":actor", new AttributeValue().withS("Tom Hanks"),
                            ":r1", new AttributeValue().withS("A"),
                            ":r2", new AttributeValue().withS("M")
                        )
                );

        QueryResult result = AmazonDynamoDBFactory.getAmazonDynamoDB().query(query);
        List<Map<String, AttributeValue>> rows = result.getItems();
        Assertions.assertEquals(rows.size(), 1);
    }

    @Test
    public void testQueryBySecondaryIndex() {
        QueryRequest query = new QueryRequest()
                .withTableName(TABLE_NAME)
                .withIndexName(INDEX_NAME)
                .withKeyConditionExpression("#m = :movie")
                .withExpressionAttributeNames(Map.of("#m", "movie"))
                .withExpressionAttributeValues(Map.of(":movie", new AttributeValue().withS("Toy Story")));

        QueryResult result = AmazonDynamoDBFactory.getAmazonDynamoDB().query(query);
        List<Map<String, AttributeValue>> rows = result.getItems();
        Assertions.assertEquals(rows.size(), 2);
    }

    @Test
    public void testScan() {
        ScanRequest scanRequest = new ScanRequest()
                .withTableName(TABLE_NAME)
                .withFilterExpression("#y <= :year")
                .withExpressionAttributeNames(Map.of("#y", "year"))
                .withExpressionAttributeValues(Map.of(":year", new AttributeValue().withN("2000")));
        ScanResult result = AmazonDynamoDBFactory.getAmazonDynamoDB().scan(scanRequest);

        List<Map<String, AttributeValue>> rows = result.getItems();
        Assertions.assertEquals(rows.size(), 3);
    }
}
