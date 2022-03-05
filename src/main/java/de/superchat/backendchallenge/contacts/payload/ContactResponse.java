package de.superchat.backendchallenge.contacts.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ContactResponse {

    private String name;
    private String lastName;
    private String email;
    private String phone;
    private LocalDateTime createdAt;

}
