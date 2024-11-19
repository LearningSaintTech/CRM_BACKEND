package com.example.springsocial.security;

import com.example.springsocial.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserPrincipal implements OAuth2User, UserDetails {
    private Long id;
    private String email;
    private String password;
    private String name;  // Add a field for the name
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;
    private Boolean profileFlag;

    public UserPrincipal(Long id, String email, String password,String name,Boolean profileFlag, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name; // Initialize the name
        this.authorities = authorities;
        this.profileFlag=profileFlag;
    }

    public static UserPrincipal create(User user) {
    	  List<GrantedAuthority> authorities = user.getRoles().stream()
                  .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                  .collect(Collectors.toList());
    	  return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getProfileFlag(),
                authorities
        );
    }


    public Boolean getProfileFlag() {
		return profileFlag;
	}

	public void setProfileFlag(Boolean profileFlag) {
		this.profileFlag = profileFlag;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return name;  // Return the actual name
    }
}
