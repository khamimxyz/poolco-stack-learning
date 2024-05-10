package xyz.khamim.slash.learn.dynamodb;

import com.amazonaws.services.dynamodbv2.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import xyz.khamim.slash.learn.dynamodb.factory.AmazonDynamoDBFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EcommerceDynamodbTest {

    private static final String TABLE_NAME = "Ecommerce";

    private static final String[] CUSTOMERS = new String[]{
            "user1,user1@gmail.com,user 1",
            "user2,user2@gmail.com,user 2",
            "user3,user3@gmail.com,user 3"
    };

    private static final String[] ADDRESSES = new String[] {
            "user1,address1OfUser1",
            "user1,address2OfUser2",
            "user2,address1OfUser2",
            "user3,address1OfUser3"
    };

    private static final String[] ORDERS = new String[] {
            "user1,O01,COMPLETED,200,5,2024-05-04 10:00:00",
            "user1,O02,ON DELIVERY,200,5,2024-05-08 10:00:00",
            "user1,O03,CANCELLED,200,5,2024-05-08 13:00:00"
    };

    @BeforeAll
    public static void initTable() {
        CreateTableRequest request = new CreateTableRequest()
                .withTableName(TABLE_NAME)
                .withKeySchema(
                        new KeySchemaElement("pk", KeyType.HASH),
                        new KeySchemaElement("sk", KeyType.RANGE)
                )
                .withAttributeDefinitions(
                        new AttributeDefinition("pk", ScalarAttributeType.S),
                        new AttributeDefinition("sk", ScalarAttributeType.S)
                )
                .withProvisionedThroughput(new ProvisionedThroughput(5L, 5L));

        AmazonDynamoDBFactory.getAmazonDynamoDB().createTable(request);
        Map<String, List<String>> addressMap = new HashMap<>();
        for (String addr : ADDRESSES) {
            String[] addrArr = addr.split(",");
            addressMap.computeIfAbsent(addrArr[0], k -> new ArrayList<>()).add(addrArr[1]);
        }

        for (String customer : CUSTOMERS) {
            String[] custArr = customer.split(",");
            final PutItemRequest putItemRequest = new PutItemRequest()
                    .withTableName(TABLE_NAME)
                    .withItem(Map.of(
                            "pk", new AttributeValue().withS("CUSTOMER#"+custArr[0]),
                            "sk", new AttributeValue().withS("CUSTOMER#"+custArr[0]),
                            "username", new AttributeValue().withS(custArr[0]),
                            "email", new AttributeValue().withS(custArr[1]),
                            "fullName", new AttributeValue().withS(custArr[2]),
                            "addresses", new AttributeValue().withSS(addressMap.get(custArr[0]))
                    ));
            AmazonDynamoDBFactory.getAmazonDynamoDB().putItem(putItemRequest);
        }

        for (String order : ORDERS) {
            String[] orderArr = order.split(",");
            final PutItemRequest putItemRequest = new PutItemRequest()
                    .withTableName(TABLE_NAME)
                    .withItem(Map.of(
                            "pk", new AttributeValue().withS("CUSTOMER#"+orderArr[0]),
                            "sk", new AttributeValue().withS("#ORDER#"+orderArr[1]),
                            "orderNumber", new AttributeValue().withS(orderArr[1]),
                            "status", new AttributeValue().withS(orderArr[2]),
                            "amount", new AttributeValue().withN(orderArr[3]),
                            "numberOfItem", new AttributeValue().withN(orderArr[4]),
                            "orderCreation", new AttributeValue().withS(orderArr[5])
                    ));
            AmazonDynamoDBFactory.getAmazonDynamoDB().putItem(putItemRequest);
        }
    }

    @Test
    public void testPutNonUniqueUsernameShouldFail() {

        PutItemRequest request = new PutItemRequest()
                .withTableName(TABLE_NAME)
                .withItem(
                    Map.of(
                        "pk", new AttributeValue().withS("CUSTOMER#user1"),
                        "sk", new AttributeValue().withS("CUSTOMER#user1"),
                        "username", new AttributeValue().withS("user1"),
                        "email", new AttributeValue().withS("myuser1@gmail.com"),
                        "fullName", new AttributeValue().withS("my user1"),
                        "addresses", new AttributeValue().withSS(List.of("addr0"))
                    ))
                .withConditionExpression("attribute_not_exists(pk)");
        Assertions.assertThrows(ConditionalCheckFailedException.class,
                () -> AmazonDynamoDBFactory.getAmazonDynamoDB().putItem(request));
    }

    @Test
    public void testGetUserAndItsLastOrderShouldSuccess() {

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(TABLE_NAME)
                .withKeyConditionExpression("#pk = :pk")
                .withExpressionAttributeNames(Map.of("#pk", "pk"))
                .withExpressionAttributeValues(Map.of(":pk", new AttributeValue().withS("CUSTOMER#user1")))
                .withScanIndexForward(false)
                .withLimit(2);

        QueryResult result = AmazonDynamoDBFactory.getAmazonDynamoDB().query(queryRequest);
        List<Map<String, AttributeValue>> items = result.getItems();
        Assertions.assertEquals(2, items.size());
        Map<String, AttributeValue> customerInfo = items.get(0);
        Map<String, AttributeValue> orderInfo = items.get(1);
        Assertions.assertEquals("user1@gmail.com", customerInfo.get("email").getS());
        Assertions.assertEquals("O03", orderInfo.get("orderNumber").getS());
    }
}
