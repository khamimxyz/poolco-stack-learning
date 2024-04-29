package xyz.khamim.awscognito.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthenticationResultType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthResponse;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CognitoAuthHelper {

    private final CognitoIdentityProviderClient cognitoClient;

    @Value("${aws.cognito.clientId}")
    private String clientId;

    public AuthenticationResultType authenticateWithCognito(String email, String password) {
        AuthenticationResultType result = null;
        InitiateAuthRequest authRequest = InitiateAuthRequest.builder()
                .clientId(clientId)
                .authFlow("USER_PASSWORD_AUTH")
                .authParameters(Map.of(
                        "USERNAME", email,
                        "PASSWORD", password
                ))
                .build();

        try {
            InitiateAuthResponse authResponse = cognitoClient.initiateAuth(authRequest);
            result = authResponse.authenticationResult();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }
}
