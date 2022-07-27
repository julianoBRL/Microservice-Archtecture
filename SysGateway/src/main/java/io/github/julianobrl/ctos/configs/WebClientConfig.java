package io.github.julianobrl.ctos.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.resolver.DefaultAddressResolverGroup;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    @Bean
    HttpClient httpClientReactor() {
        return HttpClient.create()
                .resolver(DefaultAddressResolverGroup.INSTANCE);
    }

    @Bean
	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
	    http.csrf(csrf -> csrf.disable());
	    return http.build();
	}

    @Bean
    WebClient.Builder loadBalancedWebClientBuilder(HttpClient httpClient) {
        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient));
    }

}