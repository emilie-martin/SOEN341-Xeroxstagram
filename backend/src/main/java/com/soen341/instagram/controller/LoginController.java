package com.soen341.instagram.controller;

import com.soen341.instagram.dto.account.LoginRequestDTO;
import com.soen341.instagram.dto.account.RefreshLoginRequestDTO;
import com.soen341.instagram.service.impl.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
public class LoginController
{

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "account/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO)
    {
        return loginService.login(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
    }

    @PostMapping(value = "account/refresh")
    public ResponseEntity<String> refreshToken(@RequestBody @Valid RefreshLoginRequestDTO refreshLoginRequestDTO)
    {
        return loginService.refreshToken(refreshLoginRequestDTO.getToken());
    }
}
