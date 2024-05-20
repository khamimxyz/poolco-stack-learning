package xyz.khamim.slash.ecommerce.graphql.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDbConfig {

  @Bean
  public AmazonDynamoDB amazonDynamoDB() {

    String fakeAccessKey = "test";
    String fakeSecretKey = "test";
    String region = "ap-southeast-1";

    BasicAWSCredentials awsCreds = new BasicAWSCredentials(fakeAccessKey, fakeSecretKey);

    return AmazonDynamoDBClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
          "http://localhost:4566", region))
        .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
        .build();
  }
}
