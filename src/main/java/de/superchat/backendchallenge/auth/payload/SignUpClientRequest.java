package de.superchat.backendchallenge.auth.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SignUpClientRequest {

    @NotNull
    private String name;

    @NotNull
    private String email;

}
