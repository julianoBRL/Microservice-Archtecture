package julianobrl.github.io.smartcity;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import de.codecentric.boot.admin.server.config.AdminServerProperties;

@Configuration
public class WebSecurityConfig {
	
	@Autowired
	private AdminServerProperties adminServer;
	    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	SavedRequestAwareAuthenticationSuccessHandler successHandler = 
    	          new SavedRequestAwareAuthenticationSuccessHandler();
    	        successHandler.setTargetUrlParameter("redirectTo");
    	        successHandler.setDefaultTargetUrl(this.adminServer.getContextPath() + "/");
    	        
    	http
        .authorizeRequests()
            .antMatchers(this.adminServer.getContextPath() + "/assets/**").permitAll()
            .antMatchers(this.adminServer.getContextPath() + "/login").permitAll()

            .antMatchers(this.adminServer.getContextPath() + "/instances").hasAnyRole("APP","ADMIN")
            .antMatchers(this.adminServer.getContextPath() + "/actuator/**").hasAnyRole("APP")
            
            .antMatchers(this.adminServer.getContextPath() + "/**").hasAnyRole("ADMIN")
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage(this.adminServer.getContextPath() + "/login")
            .successHandler(successHandler)
            .failureForwardUrl(this.adminServer.getContextPath() + "/login")
            .and()
        .logout()
            .logoutUrl(this.adminServer.getContextPath() + "/logout")
            .and()
        .httpBasic()
            .and()
        .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .ignoringRequestMatchers(
              new AntPathRequestMatcher(this.adminServer.getContextPath() + 
                "/instances", HttpMethod.POST.toString()), 
              new AntPathRequestMatcher(this.adminServer.getContextPath() + 
                "/instances/*", HttpMethod.DELETE.toString()),
              new AntPathRequestMatcher(this.adminServer.getContextPath() + "/actuator/**"))
            .and()
        .rememberMe()
            .key(UUID.randomUUID().toString())
            .tokenValiditySeconds(1209600);
        return http.build();
    }
    
    @Bean
    InMemoryUserDetailsManager userDetailsService() {
        UserDetails adminUser = User.builder()
            .username("admin")
            .password("{noop}admin")
            .roles("ADMIN")
            .build();
        
        UserDetails appUser = User.builder()
                .username("app")
                .password("{noop}app")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(adminUser,appUser);
    }
    
}