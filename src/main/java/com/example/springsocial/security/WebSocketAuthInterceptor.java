package com.example.springsocial.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.util.StringUtils;

import java.util.Map;

public class WebSocketAuthInterceptor implements HandshakeInterceptor {
    
    private final TokenProvider tokenProvider;

    public WebSocketAuthInterceptor(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, 
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // First try to extract the JWT from the Authorization header
        String jwt = getJwtFromRequest(request);
        System.out.println("Extracted JWT from Authorization header: " + jwt);  // Log extracted JWT from header

        // If JWT is not found in header, try to extract from query parameters
        if (jwt == null) {
            jwt = getJwtFromRequestParams(request);
            System.out.println("Extracted JWT from query parameters: " + jwt);  // Log extracted JWT from query params
        }

        // If JWT is valid, set the user ID in attributes
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Long userId = tokenProvider.getUserIdFromToken(jwt);
            attributes.put("userId", userId);
            System.out.println("User authenticated with ID: " + userId);  // Log user ID
            return true;
        }

        // If token is invalid or not found, reject the handshake
        System.out.println("Invalid or missing token, rejecting handshake");  // Log rejection
        response.setStatusCode(HttpStatus.UNAUTHORIZED); // Reject if token is invalid or not found
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, 
                               WebSocketHandler wsHandler, Exception exception) {
    }
    

    // Extract JWT from Authorization header
    private String getJwtFromRequest(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }

    // Extract JWT from query parameters
    private String getJwtFromRequestParams(ServerHttpRequest request) {
        String token = request.getURI().getQuery();
        if (StringUtils.hasText(token)) {
            // Extract token from query parameter named 'token'
            String[] params = token.split("&");
            for (String param : params) {
                if (param.startsWith("token=")) {
                    return param.substring(6); // Extract the value of 'token'
                }
            }
        }
        return null;
    }
}
