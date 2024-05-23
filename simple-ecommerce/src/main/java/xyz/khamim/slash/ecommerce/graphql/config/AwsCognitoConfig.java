package xyz.khamim.slash.ecommerce.graphql.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

@Configuration
public class AwsCognitoConfig {
  @Value("${aws.accessKeyId}")
  private String accessKeyId;
  @Value("${aws.secretAccessKey}")
  private String secretAccessKey;

  @Bean
  public CognitoIdentityProviderClient cognitoClient() {
    return CognitoIdentityProviderClient.builder()
      .credentialsProvider(() -> AwsBasicCredentials.create(accessKeyId, secretAccessKey))
      .region(Region.AP_SOUTHEAST_1)
      .build();
  }
}

