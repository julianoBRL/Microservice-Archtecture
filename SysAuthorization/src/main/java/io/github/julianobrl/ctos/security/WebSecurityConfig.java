package io.github.julianobrl.archtecture.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.julianobrl.archtecture.security.filters.JWTAuthenticationFilter;
import io.github.julianobrl.archtecture.security.filters.JWTVerifierFilter;
import io.github.julianobrl.archtecture.security.service.ApplicationUserDetailsService;
import io.github.julianobrl.archtecture.services.TokensService;


@Configuration
@EnableWebSecurity
@SuppressWarnings("deprecation")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ApplicationUserDetailsService applicationUserDetailsService;

    @Autowired
    private TokensService redisService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), redisService))
                .addFilterAfter(new JWTVerifierFilter(redisService), JWTAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/v1/validateConnection/whitelisted").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/actuator").permitAll()
                .anyRequest()
                .authenticated()
                .and().httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(encoder);
        authenticationProvider.setUserDetailsService(applicationUserDetailsService);

        return authenticationProvider;
    }
}