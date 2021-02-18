package com.aws.vydya.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.aws.vydya.controllers.CustomAuthenticationProviderService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationProviderService authenticationProviderService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProviderService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/webjars/**","/user/ssologin","/user/check").permitAll()
		.anyRequest().authenticated()
        .and().httpBasic();
	}

}


//http.csrf().disable().authorizeRequests().antMatchers("/webjars/**","/user/ssologin","/user/check").permitAll()
//.anyRequest().authenticated().and()
//.formLogin().loginPage("/user/login").permitAll().and()
//.logout().deleteCookies("remember-me").permitAll().and()
//.rememberMe().tokenValiditySeconds(180); 