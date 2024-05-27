package xyz.khamim.slash.ecommerce.graphql.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.khamim.slash.ecommerce.graphql.security.helper.CognitoRoleReader;
import xyz.khamim.slash.ecommerce.graphql.security.opa.OpaClient;
import xyz.khamim.slash.ecommerce.graphql.security.opa.OpaDataRequest;
import xyz.khamim.slash.ecommerce.graphql.security.opa.OpaDataResponse;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OpaAuthorizationManager {

    private final OpaClient opaClient;

    private final CognitoRoleReader cognitoRoleReader;

    public boolean check(final String token, final String module) {

        boolean allow = false;
        final String[] roles = cognitoRoleReader.getRoles(token);
        if(roles.length > 0) {
            final Map<String, Object> input = new HashMap<>();
            input.put("module", module);
            input.put("role", cognitoRoleReader.getRoles(token)[0]);
            final OpaDataResponse opaDataResponse = opaClient.authorizedToAccessAPI(new OpaDataRequest(input));
            allow = opaDataResponse.getResult().getAllow();
        }

        return allow;
    }
}
