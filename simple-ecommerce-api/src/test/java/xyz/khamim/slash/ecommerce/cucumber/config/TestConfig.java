package xyz.khamim.slash.ecommerce.cucumber.config;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class TestConfig {

    @Bean
    public HttpMessageConverters jacksonConverter() {
        HttpMessageConverter<?> jacksonConverter = new MappingJackson2HttpMessageConverter();
        return new HttpMessageConverters(jacksonConverter);
    }
}