package io.github.julianobrl.archtecture.model;

import lombok.Data;

@Data
public class JwtAuthenticationModel {
	 private String username;
	 private String password;
}
