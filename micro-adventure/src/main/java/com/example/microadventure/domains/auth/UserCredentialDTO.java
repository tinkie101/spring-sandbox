package com.example.microadventure.domains.auth;

import lombok.Data;

@Data
public class UserCredentialDTO {
    private final GrantType grant_type;
    private final String username;
    private final String password;
    private final String refresh_token;
}
