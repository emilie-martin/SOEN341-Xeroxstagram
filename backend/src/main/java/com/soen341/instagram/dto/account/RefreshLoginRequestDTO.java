package com.soen341.instagram.dto.account;

import javax.validation.constraints.NotNull;

public class RefreshLoginRequestDTO {
    @NotNull
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
