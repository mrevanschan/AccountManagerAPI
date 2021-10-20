package com.acmebank.accountmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AuthenticationResponse {
    final String jwt;
}
