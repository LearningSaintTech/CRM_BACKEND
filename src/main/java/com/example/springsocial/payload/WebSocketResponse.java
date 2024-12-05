package com.example.springsocial.payload;

public class WebSocketResponse {
    private String type;
    private Object data;

    // No-args constructor
    public WebSocketResponse() {
    }

    // All-args constructor
    public WebSocketResponse(String type, Object data) {
        this.type = type;
        this.data = data;
    }

    // Builder pattern
    public static WebSocketResponseBuilder builder() {
        return new WebSocketResponseBuilder();
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    // toString method
    @Override
    public String toString() {
        return "WebSocketResponse{" +
                "type='" + type + '\'' +
                ", data=" + data +
                '}';
    }

    // Builder class for the Builder pattern
    public static class WebSocketResponseBuilder {
        private String type;
        private Object data;

        public WebSocketResponseBuilder type(String type) {
            this.type = type;
            return this;
        }

        public WebSocketResponseBuilder data(Object data) {
            this.data = data;
            return this;
        }

        public WebSocketResponse build() {
            return new WebSocketResponse(type, data);
        }
    }
}

