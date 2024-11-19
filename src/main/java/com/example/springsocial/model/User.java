package com.example.springsocial.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;



@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reset_otp")
    private String resetOtp;

    @Column(name = "otp_expiry")
    private LocalDateTime otpExpiry;

    @Column(nullable = false)
    private Boolean profileFlag = false;
    
    public String getResetOtp() {
		return resetOtp;
	}

	public void setResetOtp(String resetOtp) {
		this.resetOtp = resetOtp;
	}

	public LocalDateTime getOtpExpiry() {
		return otpExpiry;
	}

	public void setOtpExpiry(LocalDateTime otpExpiry) {
		this.otpExpiry = otpExpiry;
	}

	
	
	@Column(nullable = false)
    private String name;
    
    @Column(name = "otp", nullable = false)
    private String otp;

    @Column(nullable = false)
    private Boolean emailVerified = false; 

    @OneToMany(mappedBy = "swiper", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Swipe> swipesMade;

    @OneToMany(mappedBy = "swipee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Swipe> swipesReceived;

    @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Match> matchesInitiated;

    @OneToMany(mappedBy = "user2", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Match> matchesReceived;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @NotEmpty(message = "At least one role must be selected")
    private List<Role> roles = new ArrayList<>();// Initialize roles as a mutable list

    public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AuthProvider getProvider() {
		return provider;
	}

	public void setProvider(AuthProvider provider) {
		this.provider = provider;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Email
    @Column(nullable = false)
    private String email;

    private String imageUrl;

   
    @JsonIgnore
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserProfile userProfile;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @Column(nullable = false)
    private String status;

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public Boolean getProfileFlag() {
		return profileFlag;
	}

	public void setProfileFlag(Boolean profileFlag) {
		this.profileFlag = profileFlag;
	}

	public List<Swipe> getSwipesMade() {
		return swipesMade;
	}

	public void setSwipesMade(List<Swipe> swipesMade) {
		this.swipesMade = swipesMade;
	}

	public List<Swipe> getSwipesReceived() {
		return swipesReceived;
	}

	public void setSwipesReceived(List<Swipe> swipesReceived) {
		this.swipesReceived = swipesReceived;
	}

	public List<Match> getMatchesInitiated() {
		return matchesInitiated;
	}

	public void setMatchesInitiated(List<Match> matchesInitiated) {
		this.matchesInitiated = matchesInitiated;
	}

	public List<Match> getMatchesReceived() {
		return matchesReceived;
	}

	public void setMatchesReceived(List<Match> matchesReceived) {
		this.matchesReceived = matchesReceived;
	}

    
}
