package com.soen341.instagram.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter
{
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public final static String CLIENT_ID = "instagram_client";
	public final static String CLIENT_SECRET = "secret";
	private final static int TOKEN_VALIDITY_SECONDS = 6000;

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer security) throws Exception
	{
		// returns true if user not anonymous
		security.checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception
	{
		clients.inMemory().withClient(CLIENT_ID).authorizedGrantTypes("client_credentials", "password", "refresh_token")
				.authorities().scopes("read", "write", "trust").resourceIds("oauth2-resource")
				.accessTokenValiditySeconds(TOKEN_VALIDITY_SECONDS) // Token time subject to change
				.secret(passwordEncoder.encode(CLIENT_SECRET));
	}

	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception
	{
		endpoints.authenticationManager(authenticationManager);
	}
}
