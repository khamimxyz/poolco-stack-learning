package xyz.khamim.slash.learn.dynamodb.factory;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;

public class AmazonDynamoDBFactory {

    private static volatile AmazonDynamoDB amazonDynamoDB;

    public static AmazonDynamoDB getAmazonDynamoDB() {
        if (amazonDynamoDB == null) {
            synchronized (AmazonDynamoDBFactory.class) {
                if (amazonDynamoDB == null) {
                    amazonDynamoDB = DynamoDBEmbedded.create().amazonDynamoDB();
                }
            }
        }
        return amazonDynamoDB;
    }
}
