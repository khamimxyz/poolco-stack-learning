package xyz.khamim.slash.ecommerce.graphql.config;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

@Configuration
public class HttpMessageConverterConfig {

    @Bean
    public HttpMessageConverters httpMessageConverters() {
        return new HttpMessageConverters(
                new StringHttpMessageConverter(),
                new ByteArrayHttpMessageConverter()
        );
    }
}
