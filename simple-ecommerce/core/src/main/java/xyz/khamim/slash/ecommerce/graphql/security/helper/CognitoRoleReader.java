package xyz.khamim.slash.ecommerce.graphql.security.helper;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

@Component
@Slf4j
public class CognitoRoleReader {

    public String[] getRoles(String token) {
        String[] groups = new String[]{};
        try {
            final SignedJWT signedJWT = SignedJWT.parse(token);
            final JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            final Date expirationTime = claims.getExpirationTime();
            if (expirationTime != null && new Date().before(expirationTime)) {
                groups = claims.getStringArrayClaim("cognito:groups");
            }
        } catch (ParseException e) {
            groups = new String[]{};
            log.error(e.getMessage(), e);
        }

        return groups;
    }
}
