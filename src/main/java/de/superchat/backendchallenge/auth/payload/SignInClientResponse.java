package de.superchat.backendchallenge.auth.payload;

import de.superchat.backendchallenge.shared.domain.Role;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SignInClientResponse {

    private final Long id;
    private final String email;
    private final String name;
    private final String jwtToken;
    private final Role role;

}
