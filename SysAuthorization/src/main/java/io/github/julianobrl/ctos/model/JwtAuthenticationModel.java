package io.github.julianobrl.ctos.model;

import lombok.Data;

@Data
public class JwtAuthenticationModel {
	 private String username;
	 private String password;
}
