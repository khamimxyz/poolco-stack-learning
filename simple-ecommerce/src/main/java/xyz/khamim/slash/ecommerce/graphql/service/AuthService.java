package xyz.khamim.slash.ecommerce.graphql.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;
import xyz.khamim.slash.ecommerce.graphql.input.AuthReq;
import xyz.khamim.slash.ecommerce.graphql.input.VerifyReq;
import xyz.khamim.slash.ecommerce.graphql.service.helper.CognitoAccountHelper;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

  private final CognitoIdentityProviderClient cognitoClient;

  private final CognitoAccountHelper accountHelper;

  @Value("${aws.cognito.clientId}")
  private String clientId;

  @Value("${aws.cognito.userPoolId}")
  private String userPoolId;

  public String auth(AuthReq authReq) {

    String session = null;
    boolean registerSuccess = true;
    if (!accountHelper.checkAccount(authReq.getEmail())) {
      registerSuccess = accountHelper.register(authReq.getEmail());
    }

    if (registerSuccess) {
      session = accountHelper.initiateAuth(authReq.getEmail(), authReq.getGroup());
    }

    return session;
  }

  public String verify(final VerifyReq verifyReq) throws RuntimeException {
    final String token;
    try {
      AdminRespondToAuthChallengeRequest challengeRequest = AdminRespondToAuthChallengeRequest.builder()
        .challengeName(ChallengeNameType.CUSTOM_CHALLENGE)
        .clientId(clientId)
        .userPoolId(userPoolId)
        .session(verifyReq.getSession())
        .challengeResponses(Map.of(
          "USERNAME", verifyReq.getEmail(),
          "ANSWER", verifyReq.getCode()
        ))
        .build();
      AdminRespondToAuthChallengeResponse challengeResponse = cognitoClient.adminRespondToAuthChallenge(challengeRequest);
      token = challengeResponse.authenticationResult().idToken();
    } catch (CognitoIdentityProviderException e) {
      throw new RuntimeException("Verify auth code failed: " + e.awsErrorDetails().errorMessage());
    }

    return token;
  }
}
