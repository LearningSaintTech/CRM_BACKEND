package com.example.springsocial.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.example.springsocial.security.TokenProvider;
import com.example.springsocial.security.WebSocketAuthInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private TokenProvider tokenProvider;

    public WebSocketConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new YourWebSocketHandler(), "/ws")
                .addInterceptors(new WebSocketAuthInterceptor(tokenProvider))
                .setAllowedOrigins("*");
    }
}

