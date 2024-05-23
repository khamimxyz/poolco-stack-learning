package xyz.khamim.slash.ecommerce.graphql.security.helper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class OpaPolicyInitializer {

    private final ResourceLoader resourceLoader;
    private final WebClient.Builder webClientBuilder;

    @Value("${opa.authorization.url}")
    private String opaAuthorizationUrl;

    @Value("${opa.policy.location}")
    private String opaPolicyLocation;

    @PostConstruct
    void initOpaPolicy() {

        try {
            Resource resource = resourceLoader.getResource(opaPolicyLocation);
            String policy = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            WebClient webClient = webClientBuilder
                    .baseUrl(opaAuthorizationUrl)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();
            webClient.put()
                    .uri("/v1/policies/ecommerce.authorization")
                    .bodyValue(policy)
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe(
                            result -> log.info("Policy uploaded successfully"),
                            error -> log.error("Error uploading policy: " + error.getMessage())
                    );
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
