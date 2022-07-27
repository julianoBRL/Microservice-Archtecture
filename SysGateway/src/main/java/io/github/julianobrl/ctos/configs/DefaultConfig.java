package io.github.julianobrl.archtecture.configs;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.julianobrl.archtecture.filters.AuthenticationPrefilter;

@Configuration
public class DefaultConfig {
	
	@Value("${spring.gateway.excludedURLsNew}")
    private String urlsStrings;


    @Bean
    ObjectMapper objectMapper() {
        JsonFactory factory = new JsonFactory();
        factory.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);

        ObjectMapper objectMapper = new ObjectMapper(factory);
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        return objectMapper;
    }


    @Bean
    RouteLocator routes(RouteLocatorBuilder builder,AuthenticationPrefilter authFilter) {
        return builder.routes()
                .route("auth", r -> 
                	   r.path("/auth/login")
                		.or().path("/auth/register")
                        .uri("lb://SYSAUTHORIZATION"))
                .build();
        
        /*
         * .route("auth", r -> r.path("/auth/**")
                        .filters(f ->f.rewritePath("/auth/(?<segment>/?.*)", "$\\{segment}"))
                        .uri("lb://SYSAUTHORIZATION"))
                .build();
         */
    }
}