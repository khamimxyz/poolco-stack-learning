package xyz.khamim.slash.ecommerce.graphql.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.awscore.exception.AwsErrorDetails;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminRespondToAuthChallengeRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminRespondToAuthChallengeResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthenticationResultType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import xyz.khamim.slash.ecommerce.graphql.input.AuthReq;
import xyz.khamim.slash.ecommerce.graphql.input.VerifyReq;
import xyz.khamim.slash.ecommerce.graphql.service.AuthService;
import xyz.khamim.slash.ecommerce.graphql.service.helper.CognitoAccountHelper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

  @Mock
  private CognitoIdentityProviderClient cognitoClient;

  @Mock
  private CognitoAccountHelper accountHelper;

  @InjectMocks
  private AuthService authService;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  private final String email = "email1@mydomain.me";

  private final VerifyReq verifyReq = VerifyReq.builder()
    .session("dummy-session")
    .email(email)
    .code("12345")
    .build();


  @Test
  void auth_ShouldRegisterFirstWhenAccountDoesNotExists() {

    final AuthReq authReq = AuthReq.builder()
      .email(email)
      .group("Customer")
      .build();

    when(accountHelper.checkAccount(authReq.getEmail()))
      .thenReturn(false);

    authService.auth(authReq);
    verify(accountHelper, times(1)).register(authReq.getEmail());
  }

  @Test
  void auth_ShouldDirectlyInitiateAuthWhenAccountAlreadyExists() {
    final AuthReq authReq = AuthReq.builder()
      .email(email)
      .group("Customer")
      .build();

    when(accountHelper.checkAccount(authReq.getEmail()))
      .thenReturn(true);

    authService.auth(authReq);
    verify(accountHelper, times(0)).register(authReq.getEmail());
  }

  @Test
  void verify_ShouldReturnTokenWhenSuccess() {

    final String expectedToken = "dummy-token";
    when(cognitoClient.adminRespondToAuthChallenge(any(AdminRespondToAuthChallengeRequest.class)))
      .thenReturn(
        AdminRespondToAuthChallengeResponse.builder()
          .authenticationResult(
            AuthenticationResultType.builder().idToken("dummy-token").build()
          )
          .build()
      );

    final String token = authService.verify(verifyReq);

    Assertions.assertThat(token).isEqualTo(expectedToken);
  }

  @Test
  void verify_ShouldReturnEmptyTokenWhenFailed() {

    when(cognitoClient.adminRespondToAuthChallenge(any(AdminRespondToAuthChallengeRequest.class)))
      .thenThrow(
        CognitoIdentityProviderException.builder()
          .awsErrorDetails(
            AwsErrorDetails.builder()
              .errorMessage("invalid")
              .build()
          ).build()
      );

    RuntimeException thrown = Assertions.catchThrowableOfType(() -> authService.verify(verifyReq),
      RuntimeException.class);

    Assertions.assertThat(thrown)
      .isInstanceOf(RuntimeException.class)
      .hasMessageContaining("invalid");

  }
}