package xyz.khamim.slash.ecommerce.security.helper;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
@Slf4j
public class CognitoRoleReader {

    public String[] getRoles(String token) {
        String[] groups;
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            groups = claims.getStringArrayClaim("cognito:groups");
        } catch (ParseException e) {
            groups = new String[]{};
            log.error(e.getMessage(), e);
        }

        return groups;
    }
}
