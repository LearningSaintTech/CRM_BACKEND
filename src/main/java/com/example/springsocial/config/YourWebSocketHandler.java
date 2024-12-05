package com.example.springsocial.config;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CopyOnWriteArrayList;

public class YourWebSocketHandler extends TextWebSocketHandler {

    // A thread-safe list to manage active sessions
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Add the session to the list of active sessions
        sessions.add(session);
        System.out.println("Connection established. Session ID: " + session.getId());

        // Log the attributes for debugging
        Object userId = session.getAttributes().get("userId");
        if (userId != null) {
            System.out.println("User authenticated with ID: " + userId);
        } else {
            System.out.println("No user ID found in session attributes.");
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle incoming WebSocket messages
        String payload = message.getPayload();
        System.out.println("Received message: " + payload + " from Session ID: " + session.getId());

        // Echo the received message to all connected clients
        for (WebSocketSession activeSession : sessions) {
            if (activeSession.isOpen()) {
                System.out.println("Sending message to Session ID: " + activeSession.getId());
                activeSession.sendMessage(new TextMessage("Server: " + payload));
            } else {
                System.out.println("Session ID: " + activeSession.getId() + " is closed.");
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Remove the session when the connection is closed
        sessions.remove(session);
        System.out.println("Connection closed. Session ID: " + session.getId() + ". Reason: " + status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // Handle any errors that occur during WebSocket communication
        System.err.println("Error in WebSocket session " + session.getId() + ": " + exception.getMessage());
        session.close(CloseStatus.SERVER_ERROR);
    }
}
