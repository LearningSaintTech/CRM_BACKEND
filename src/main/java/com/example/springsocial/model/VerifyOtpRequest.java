package com.example.springsocial.model;

public class VerifyOtpRequest {
    private String email;
    private String otp;

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

	@Override
	public String toString() {
		return "VerifyOtpRequest [email=" + email + ", otp=" + otp + "]";
	}
    
    
    
}

