package de.superchat.backendchallenge.auth.payload;

import lombok.Data;

@Data
public class SignUpClientResponse {

    private final String message;
    private final String email;
    // Returning password just to accomplish with code challenge
    // Password should be sent by email
    private final String password;

}
