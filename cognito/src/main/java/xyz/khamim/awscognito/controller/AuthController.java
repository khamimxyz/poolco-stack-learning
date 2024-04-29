package xyz.khamim.awscognito.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthenticationResultType;
import xyz.khamim.awscognito.auth.CognitoAuthHelper;
import xyz.khamim.awscognito.payload.AuthRequest;

@RestController
@AllArgsConstructor
public class AuthController {

    private final CognitoAuthHelper cognitoAuthHelper;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest authRequest) {
        String email = authRequest.getEmail();
        String password = authRequest.getPassword();

        AuthenticationResultType authResult = cognitoAuthHelper.authenticateWithCognito(email, password);
        if (authResult != null) {
            return ResponseEntity.ok(authResult.accessToken());
        } else {
            return ResponseEntity.badRequest().body("Authentication failed");
        }
    }

    @PostMapping("/secured")
    public ResponseEntity<String> securedEndpoint() {
        return ResponseEntity.ok("This is a secured endpoint.");
    }
}