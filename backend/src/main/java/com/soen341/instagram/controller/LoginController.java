package com.soen341.instagram.controller;

import com.soen341.instagram.dao.impl.AccountRepository;
import com.soen341.instagram.dto.account.AccountDTO;
import com.soen341.instagram.dto.account.LoginRequestDTO;
import com.soen341.instagram.dto.account.RefreshLoginRequestDTO;
import com.soen341.instagram.model.Account;
import com.soen341.instagram.service.impl.LoginService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
public class LoginController
{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "account")
    public AccountDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof UserDetails))
            return null;
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        Account user = accountRepository.findByUsername(username);
        return modelMapper.map(user, AccountDTO.class);
    }

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
