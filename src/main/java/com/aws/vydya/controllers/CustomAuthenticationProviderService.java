package com.aws.vydya.controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationProviderService implements AuthenticationProvider {
	
	 
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken authenticationToken = null;
		
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		UserLogin user = getUser();
		if(user != null) {
			if(username.equals(user.getUserName())) {//&& BCrypt.checkpw(password, user.getPassword())
				Collection<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(user);
				//authenticationToken = new UsernamePasswordAuthenticationToken(
						//new org.springframework.security.core.userdetails.User(username, password, grantedAuthorities), password, grantedAuthorities);
			
				authenticationToken = new UsernamePasswordAuthenticationToken(username,password,grantedAuthorities);
			}
		} else {
			throw new UsernameNotFoundException("User name "+username+" not found");
		}
		return authenticationToken;
	}

	private Collection<GrantedAuthority> getGrantedAuthorities(UserLogin user) {
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		if(user.getRole().equals("admin")) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return grantedAuthorities;
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	private UserLogin getUser() {
		UserLogin us = new UserLogin();
		us.setUserName("imran");
		us.setPassword("Password1");
		us.setRole("admin");
		return us;
		
	}
}
