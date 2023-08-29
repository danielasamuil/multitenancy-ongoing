package com.multitenant.multitenancy.config.security.dto;

import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class SignupRequest {

    private String username;
    private String email;
    private String password;
    private Set<String> roles;
}