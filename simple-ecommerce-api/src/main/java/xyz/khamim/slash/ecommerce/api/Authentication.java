package xyz.khamim.slash.ecommerce.api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthenticationResultType;
import xyz.khamim.slash.ecommerce.security.CognitoAuthenticationManager;
import xyz.khamim.slash.ecommerce.payload.Response;
import xyz.khamim.slash.ecommerce.payload.auth.AuthRequest;
import xyz.khamim.slash.ecommerce.payload.auth.AuthResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class Authentication {

    private final CognitoAuthenticationManager cognitoAuthenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();

        Response<AuthResponse> response = new Response<>();
        AuthenticationResultType authResult = cognitoAuthenticationManager.authenticate(username, password);
        if (authResult != null) {
            response.setPayload(AuthResponse.builder().token(authResult.accessToken()).build());
        } else {
            response.setStatus("Failed");
        }

        return ResponseEntity.ok(response);
    }
}
