package com.example.px_gateway.filter;

import com.example.px_gateway.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    private final JwtProperties jwtProperties;


    public JwtAuthFilter(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        
        ServerHttpRequest request = exchange.getRequest();

        // 放行登录接口
        String path = request.getURI().getPath();
        if (jwtProperties.getWhiteList().contains(path)) {
            return chain.filter(exchange);
        }


        String token = request.getHeaders().getFirst("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        try {
            String realToken = token.replace("Bearer ", "");

            String secrt = jwtProperties.getSecret();
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtProperties.getSecret().getBytes())
                    .build()
                    .parseClaimsJws(realToken)
                    .getBody();

            // 可选：把 userId 传给下游
            String userId = claims.getSubject();

            ServerHttpRequest newRequest = exchange.getRequest().mutate()
                    .header("X-User-Id", userId)
                    .build();

            return chain.filter(exchange.mutate().request(newRequest).build());

        } catch (Exception e) {
            return unauthorized(exchange);
        }
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return 0; // 越小越先执行
    }
}