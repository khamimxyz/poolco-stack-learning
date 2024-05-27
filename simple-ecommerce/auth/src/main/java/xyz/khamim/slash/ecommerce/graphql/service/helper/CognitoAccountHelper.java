package xyz.khamim.slash.ecommerce.graphql.service.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CognitoAccountHelper {

  private final CognitoIdentityProviderClient cognitoClient;
  @Value("${aws.cognito.clientId}")
  private String clientId;

  @Value("${aws.cognito.userPoolId}")
  private String userPoolId;

  public boolean register(String email) {
    try {
      final AdminCreateUserRequest createUserRequest = AdminCreateUserRequest.builder()
        .userPoolId(userPoolId)
        .username(email)
        .userAttributes(AttributeType.builder().name("email").value(email).build())
        .messageAction(MessageActionType.SUPPRESS)
        .build();
      cognitoClient.adminCreateUser(createUserRequest);
    } catch (CognitoIdentityProviderException e) {
      throw new RuntimeException("Sign-up failed: "+e.awsErrorDetails().errorMessage());
    }

    return true;
  }

  public boolean checkAccount(String email) {
    boolean exists = true;
    try {
      final AdminGetUserRequest getUserRequest = AdminGetUserRequest.builder()
        .userPoolId(userPoolId)
        .username(email)
        .build();
      cognitoClient.adminGetUser(getUserRequest);
    } catch (UserNotFoundException e) {
      exists = false;
    } catch (Exception e) {
      exists = false;
      log.error("Exception", e);
    }

    return exists;
  }

  public String initiateAuth(String email, String groupName) {

    final String session;
    try {
      final AdminInitiateAuthRequest authRequest = AdminInitiateAuthRequest.builder()
        .authFlow(AuthFlowType.CUSTOM_AUTH)
        .clientId(clientId)
        .userPoolId(userPoolId)
        .authParameters(Map.of("USERNAME", email))
        .build();
      final AdminInitiateAuthResponse authResponse = cognitoClient.adminInitiateAuth(authRequest);
      session = authResponse.session();

      final AdminAddUserToGroupRequest addUserToGroupRequest = AdminAddUserToGroupRequest.builder()
        .groupName(groupName)
        .userPoolId(userPoolId)
        .username(email)
        .build();
      cognitoClient.adminAddUserToGroup(addUserToGroupRequest);
    } catch (CognitoIdentityProviderException e) {
      throw new RuntimeException("Initiate auth failed: " + e.awsErrorDetails().errorMessage());
    }

    return session;
  }
}
