package de.superchat.backendchallenge.contacts.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ContactRequest {

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String phone;

}
