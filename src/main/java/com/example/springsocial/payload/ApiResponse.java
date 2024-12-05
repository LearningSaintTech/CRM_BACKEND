package com.example.springsocial.payload;


/**
 * Represents the response data structure for Rest API responses.
 */
public class ApiResponse {
    private Integer statusCode;
    private String status;
    private String reason;
    private Object data;

    // Private constructor to force usage of the Builder
    private ApiResponse(Builder builder) {
        this.statusCode = builder.statusCode;
        this.status = builder.status;
        this.reason = builder.reason;
        this.data = builder.data;
    }

    public ApiResponse(boolean b, String string) {
		// TODO Auto-generated constructor stub
	}

	// Getters
    public Integer getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public Object getData() {
        return data;
    }

    // Builder class
    public static class Builder {
        private Integer statusCode;
        private String status;
        private String reason;
        private Object data;

        public Builder statusCode(Integer statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public ApiResponse build() {
            return new ApiResponse(this);
        }
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "statusCode=" + statusCode +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                ", data=" + data +
                '}';
    }

	
}

