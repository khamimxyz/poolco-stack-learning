package xyz.khamim.slash.ecommerce.api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthenticationResultType;
import xyz.khamim.slash.ecommerce.payload.auth.AuthRequest;
import xyz.khamim.slash.ecommerce.payload.auth.AuthResponse;
import xyz.khamim.slash.ecommerce.security.CognitoAuthenticationManager;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final CognitoAuthenticationManager cognitoAuthenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();

        AuthenticationResultType authResult = cognitoAuthenticationManager.authenticate(username, password);
        AuthResponse response = AuthResponse.builder().build();
        if (authResult != null) {
            response.setToken(authResult.accessToken());
        }

        return ResponseEntity.ok(response);
    }
}
