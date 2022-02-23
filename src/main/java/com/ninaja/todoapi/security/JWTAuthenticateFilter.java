package com.ninaja.todoapi.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninaja.todoapi.model.User;

public class JWTAuthenticateFilter extends UsernamePasswordAuthenticationFilter {

	 public static final int TOKEN_EXPIRATION = 600_000;
	    public static final String TOKEN_PASSWORD = "7f3e2267-61c3-4052-be9c-133c3e61ae83";

	    private final AuthenticationManager authenticationManager;

	    public JWTAuthenticateFilter(AuthenticationManager authenticationManager) {
	        this.authenticationManager = authenticationManager;
	    }
	    
		@Override
		public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
				throws AuthenticationException {
			try {
				User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

				return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),
						user.getSenha(), new ArrayList<>()));

			} catch (IOException e) {
				throw new RuntimeException("Falha ao autenticar usuario", e);
			}

		}

	    @Override
	    protected void successfulAuthentication(HttpServletRequest request,
	                                            HttpServletResponse response,
	                                            FilterChain chain,
	                                            Authentication authResult) throws IOException, ServletException {

	        UserDetailsImplements userDetail = (UserDetailsImplements) authResult.getPrincipal();

	        String token = JWT.create().
	                withSubject(userDetail.getUsername())
	                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
	                .sign(Algorithm.HMAC512(TOKEN_PASSWORD));

	        response.getWriter().write(token);
	        response.getWriter().flush();
		}

	}