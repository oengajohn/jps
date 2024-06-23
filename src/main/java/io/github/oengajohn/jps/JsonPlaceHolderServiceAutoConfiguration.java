package io.github.oengajohn.jps;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.oengajohn.jps.todo.JpsTodoClient;

@AutoConfiguration
@EnableConfigurationProperties(JsonPlaceHolderServiceProperties.class)
public class JsonPlaceHolderServiceAutoConfiguration {
    private final JsonPlaceHolderServiceProperties jsonPlaceHolderServiceProperties;

    public JsonPlaceHolderServiceAutoConfiguration(JsonPlaceHolderServiceProperties jsonPlaceHolderServiceProperties) {
        this.jsonPlaceHolderServiceProperties = jsonPlaceHolderServiceProperties;
    }

    @Bean("jpsWebClientBuilder")
    WebClient.Builder jpsWebClientBuilder() {
        return WebClient.builder().baseUrl(jsonPlaceHolderServiceProperties.baseUrl());
    }

    @Bean
    JpsTodoClient jpsTodoClient(WebClient.Builder jpsWebClientBuilder) {
        return new JpsTodoClient(jpsWebClientBuilder);
    }
}
