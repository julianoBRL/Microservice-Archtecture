package io.github.julianobrl.archtecture.security.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.julianobrl.archtecture.utils.AppConstants;

@Configuration
public class EncrypterConfig {

    @Bean
    PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(AppConstants.bCryptEncoderStrength);
    }
    
}