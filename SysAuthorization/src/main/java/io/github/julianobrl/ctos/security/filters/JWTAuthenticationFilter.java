package io.github.julianobrl.ctos.security.filters;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.julianobrl.ctos.model.ConnValidationResponse;
import io.github.julianobrl.ctos.model.JwtAuthenticationModel;
import io.github.julianobrl.ctos.model.TokensEntity;
import io.github.julianobrl.ctos.security.SecurityConstants;
import io.github.julianobrl.ctos.services.TokensService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    
    private ObjectMapper mapper = new ObjectMapper();

    private final TokensService tokensRedisService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            JwtAuthenticationModel authModel = mapper.readValue(request.getInputStream(), JwtAuthenticationModel.class);
            authModel.setPassword(authModel.getPassword());
            Authentication authentication = new UsernamePasswordAuthenticationToken(authModel.getUsername(), authModel.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    	Date expToken = Date.from(LocalDateTime.now().plusMinutes(30).toInstant(OffsetDateTime.now().getOffset()));
    	String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setIssuer(SecurityConstants.ISSUER)
                .setExpiration(expToken)
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.KEY)
                .compact();

        TokensEntity tokensEntity = TokensEntity.builder().authenticationToken(token)
                        .username(authResult.getName())
                        .createdBy("SYSTEM").createdOn(LocalDateTime.now())
                        .modifiedBy("SYSTEM").modifiedOn(LocalDateTime.now())
                        .build();
        tokensEntity = tokensRedisService.save(tokensEntity);
        response.addHeader(SecurityConstants.HEADER, String.format("Bearer %s", tokensEntity.getId()));
        response.addHeader("Expiration", String.valueOf(30*60));
        
        

        ConnValidationResponse respModel = ConnValidationResponse.builder()
        		.status(HttpStatus.OK.name())
        		.token(String.format("Bearer %s", tokensEntity.getId()))
        		.methodType(request.getMethod().toString())
        		.isAuthenticated(true)
        		.username(authResult.getName())
        		.authorities(authResult.getAuthorities().stream().collect(Collectors.toList()))
        .build();
        response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().write(mapper.writeValueAsBytes(respModel));
    }
}