package io.github.julianobrl.ctos.resources;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.julianobrl.ctos.model.ConnValidationResponse;

@RestController
@RequestMapping("/api/v1/validateToken")
public class ConnectionValidatorResource {

    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ConnValidationResponse> validateGet(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        
        @SuppressWarnings("unchecked")
		List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) request.getAttribute("authorities");
        return ResponseEntity.ok(
        		ConnValidationResponse.builder()
        			.status("OK")
        			.methodType(request.getMethod())
                    .username(username)
                    .authorities(grantedAuthorities)
                    .token((String) request.getAttribute("jwt"))
                .isAuthenticated(true).build());
    }
    
    

}