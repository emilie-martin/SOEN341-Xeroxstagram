package com.soen341.instagram.dto.account;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class AccountDTO {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Date created;
}
