package io.github.oengajohn.jps;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * JsonPlaceHolderServiceProperties
 */
@ConfigurationProperties("json-placeholder-service")
public record JsonPlaceHolderServiceProperties(
                @DefaultValue("https://jsonplaceholder.typicode.com") String baseUrl) {
}