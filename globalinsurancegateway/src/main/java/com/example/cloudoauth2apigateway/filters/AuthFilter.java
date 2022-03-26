package com.example.cloudoauth2apigateway.filters;

import com.example.cloudoauth2apigateway.dto.UserDto;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    private final WebClient.Builder webClientBuilder;

    public AuthFilter(WebClient.Builder webClientBuilder){
        super(Config.class);
        this.webClientBuilder = webClientBuilder;

    }

    public GatewayFilter apply(Config config){
        return (exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                throw new RuntimeException("Missing Authorization Information");
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

            String[] parts = authHeader.split(" ");

            if (parts.length!=2 || !"Bearer".equals(parts[0])){
                throw new RuntimeException("Incorrect Authorization Structure");
            }

            return webClientBuilder.build().post().uri("userservice/users/validateToken?token="+parts[1]).retrieve().bodyToMono(UserDto.class).map(userDto -> {
                exchange.getRequest().mutate().header("X-auth-user-id", String.valueOf(userDto.getId()));
                return exchange;
            }).flatMap(chain::filter);
        };
    }

    public static class Config{
        // needed to extend AuthFilter.config, but nothing to override
    };

}
