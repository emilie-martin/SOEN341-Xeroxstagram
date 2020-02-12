package com.soen341.instagram.service.impl;

import com.soen341.instagram.security.AuthorizationServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service("loginService")
public class LoginService {
    @Autowired
    private Environment environment;

    public ResponseEntity<String> login(String username, String password) {
        MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();
        bodyParams.add("grant_type", "password");
        bodyParams.add("username", username);
        bodyParams.add("password", password);
        return makeRequestWithBodyParams(bodyParams);
    }

    public ResponseEntity<String> refreshToken(String token) {
        MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();
        bodyParams.add("grant_type", "refresh_token");
        bodyParams.add("refresh_token", token);
        return makeRequestWithBodyParams(bodyParams);
    }

    private RestTemplate createRestTemplateBasicAuth() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(AuthorizationServerConfig.CLIENT_ID,
                AuthorizationServerConfig.CLIENT_SECRET));
        return restTemplate;
    }

    private ResponseEntity<String> makeRequestWithBodyParams(MultiValueMap<String, String> bodyParams) {
        RestTemplate restTemplate = createRestTemplateBasicAuth();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        HttpEntity<String> requestEntity = new HttpEntity(bodyParams, headers);

        return restTemplate.postForEntity("http://localhost:" + environment.getProperty("local.server.port")
                + "/oauth/token", requestEntity, String.class);
    }
}
