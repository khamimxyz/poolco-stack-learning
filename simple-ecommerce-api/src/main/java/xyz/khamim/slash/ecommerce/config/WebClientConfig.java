package xyz.khamim.slash.ecommerce.config;

import com.netflix.graphql.dgs.client.MonoGraphQLClient;
import com.netflix.graphql.dgs.client.WebClientGraphQLClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${app.graphql.endpoint}")
    private String graphqlEndpoint;

    @Bean
    public WebClientGraphQLClient webClientGraphQLClient() {
        WebClient webClient = WebClient.create(graphqlEndpoint);
        return MonoGraphQLClient.createWithWebClient(webClient);
    }
}
