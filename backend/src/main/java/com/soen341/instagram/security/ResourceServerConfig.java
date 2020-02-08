package com.soen341.instagram.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter
{
	@Autowired
	@Qualifier("UserDetailsService")
	private UserDetailsService userDetailsService;

	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		http.headers().frameOptions().disable().and().authorizeRequests().antMatchers("/", "/register", "/login")
				.permitAll();

		// I'm letting all the request as permitAll for now. It will be easier to
		// developp API. We can configure the authentication later
	}

}