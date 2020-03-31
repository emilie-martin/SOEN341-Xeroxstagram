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
		http.cors().and().headers().frameOptions().disable().and().authorizeRequests().antMatchers("/picture")
				.authenticated().antMatchers("/**/likeStatus/**").permitAll()
				.antMatchers("/comment/commentByPicture/**", "/comment/commentById/**").permitAll()
				.antMatchers("/comment/**").authenticated().antMatchers("/account/profile/{username}").permitAll()
				.antMatchers("/account/profile/**").authenticated().antMatchers("/account/following/{username}").permitAll().antMatchers("/account/following/**")
				.authenticated();
	}

}
