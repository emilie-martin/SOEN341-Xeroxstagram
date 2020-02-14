package com.soen341.instagram.dto.account;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RefreshLoginRequestDTO {
    @NotNull
    private String token;
}
