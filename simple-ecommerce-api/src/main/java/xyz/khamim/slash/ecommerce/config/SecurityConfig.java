package xyz.khamim.slash.ecommerce.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import xyz.khamim.slash.ecommerce.security.OpaAuthorizationManager;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${aws.cognito.idpUrl}")
    private String idpUrl;

    private final OpaAuthorizationManager opaAuthorizationManager;

    private final String[] securedApi = new String[]{"/product", "/promotion", "/order"};

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return NimbusReactiveJwtDecoder.withJwkSetUri(idpUrl).build();
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(exchanges -> exchanges
                        .matchers(ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/authenticate"))
                        .permitAll()
                        .matchers(ServerWebExchangeMatchers.pathMatchers(HttpMethod.GET, "/graphiql"))
                        .permitAll()
                        .matchers(ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, securedApi))
                        .access(opaAuthorizationManager)
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtDecoder(jwtDecoder())))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }
}
