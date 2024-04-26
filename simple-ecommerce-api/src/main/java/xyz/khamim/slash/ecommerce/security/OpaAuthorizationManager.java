package xyz.khamim.slash.ecommerce.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.khamim.slash.ecommerce.proxy.opa.OpaClient;
import xyz.khamim.slash.ecommerce.proxy.opa.OpaDataRequest;
import xyz.khamim.slash.ecommerce.proxy.opa.OpaDataResponse;
import xyz.khamim.slash.ecommerce.security.helper.CognitoRoleReader;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OpaAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final OpaClient opaClient;

    private final CognitoRoleReader cognitoRoleReader;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context) {
        final ServerWebExchange exchange = context.getExchange();
        final String[] path = exchange.getRequest().getURI().getPath().replaceAll("^/|/$", "").split("/");

        final String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        return authentication.map(auth -> {
            final Map<String, Object> input = new HashMap<>();
            input.put("method", exchange.getRequest().getMethod().toString());
            input.put("path", path);

            if(authHeader != null) {
                final String token = authHeader.replaceAll("Bearer ", "");
                input.put("token", token);
            }

            final OpaDataResponse opaDataResponse = opaClient.authorizedToAccessAPI(new OpaDataRequest(input));
            return new AuthorizationDecision(opaDataResponse.getResult().getAllow());
        });
    }
}
