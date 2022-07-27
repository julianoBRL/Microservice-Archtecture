package io.github.julianobrl.archtecture.model;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConnValidationResponse {
	 private String status;
	 private boolean isAuthenticated;
	 private String methodType;
	 private String username;
	 private String token;
	 private List<GrantedAuthority> authorities;
}
