package xyz.khamim.slash.ecommerce.graphql.service.helper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.awscore.exception.AwsErrorDetails;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CognitoAccountHelperTest {

  @Mock
  private CognitoIdentityProviderClient cognitoClient;

  @InjectMocks
  private CognitoAccountHelper accountHelper;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void register_ShouldReturnTrueWhenSuccess() {
    when(cognitoClient.adminCreateUser(any(AdminCreateUserRequest.class)))
      .thenReturn(AdminCreateUserResponse.builder().build());
    boolean actual = accountHelper.register("email1@mydomain.me");

    Assertions.assertThat(actual).isTrue();
  }

  @Test
  void register_ShouldReturnRuntimeExceptionWhenFailed() {
    when(cognitoClient.adminCreateUser(any(AdminCreateUserRequest.class)))
      .thenThrow(CognitoIdentityProviderException.builder().message("error").build());

    RuntimeException thrown = Assertions.catchThrowableOfType(() -> accountHelper.register(anyString()),
      RuntimeException.class);

    Assertions.assertThat(thrown)
      .isInstanceOf(RuntimeException.class)
      .hasMessageContaining("error");
  }

  @Test
  void checkAccount_ShouldReturnTrueWhenAccountExists() {

    when(cognitoClient.adminGetUser(any(AdminGetUserRequest.class)))
      .thenReturn(AdminGetUserResponse.builder().build());

    boolean actual = accountHelper.checkAccount(anyString());

    Assertions.assertThat(actual).isTrue();
  }

  @Test
  void checkAccount_ShouldReturnFalseWhenAccountDoesNotExists() {

    when(cognitoClient.adminGetUser(any(AdminGetUserRequest.class)))
      .thenThrow(UserNotFoundException.builder().message("User not found").build());

    boolean actual = accountHelper.checkAccount(anyString());

    Assertions.assertThat(actual).isFalse();
  }

  @Test
  void initiateAuth_ShouldReturnSessionWhenSucceed() {

    when(cognitoClient.adminInitiateAuth(any(AdminInitiateAuthRequest.class)))
      .thenReturn(AdminInitiateAuthResponse.builder().session("dummy-session").build());

    final String actual = accountHelper.initiateAuth(anyString(), "Customer");

    verify(cognitoClient, times(1)).adminAddUserToGroup(any(AdminAddUserToGroupRequest.class));
    Assertions.assertThat(actual).isEqualTo("dummy-session");
  }

  @Test
  void initiateAuth_ShouldThrowRuntimeExceptionWhenFailed() {

    when(cognitoClient.adminInitiateAuth(any(AdminInitiateAuthRequest.class)))
      .thenThrow(CognitoIdentityProviderException.builder()
        .awsErrorDetails(AwsErrorDetails.builder().errorMessage("error").build())
        .build());

    RuntimeException thrown = Assertions.catchThrowableOfType(
      () -> accountHelper.initiateAuth(anyString(), "Customer"),
      RuntimeException.class);

    Assertions.assertThat(thrown)
      .isInstanceOf(RuntimeException.class)
      .hasMessageContaining("error");
  }
}