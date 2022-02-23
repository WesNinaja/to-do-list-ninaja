package com.ninaja.todoapi.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class JWTConfiguration extends WebSecurityConfigurerAdapter {

	private final UserDetailsServiceImplements userService;
	private final PasswordEncoder passwordEncoder;

	public JWTConfiguration(UserDetailsServiceImplements userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
		
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		
			.antMatchers(HttpMethod.POST, "/api/v1/users/save").permitAll()
			.antMatchers(HttpMethod.POST, "/login").permitAll()			
			.anyRequest().authenticated()
			.and()
			.addFilter(new JWTAuthenticateFilter(authenticationManager()))
			.addFilter(new JWTVAlidateFilter(authenticationManager()))
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().cors()
			.and().csrf().disable();
	}
	
	  @Override
	    public void configure(WebSecurity web) throws Exception {
	        web.ignoring().antMatchers("/v3/api-docs/**",
	                                   "/configuration/ui",
	                                   "/swagger-resources/**",
	                                   "/configuration/security",
	                                   "/swagger-ui.html",
	                                   "/swagger-ui/**");
	        
	    }
	 
}