package de.superchat.backendchallenge.auth.payload;

import lombok.Data;

@Data
public class SignInClientRequest {

    private String email;
    private String password;

}
