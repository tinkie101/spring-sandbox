package com.example.microadventure.domains.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@NoArgsConstructor
public class AuthTokenDTO {
    private String access_token;
    private String refresh_token;
    private Integer expires_in;
    private Integer refresh_expires_in;
}
