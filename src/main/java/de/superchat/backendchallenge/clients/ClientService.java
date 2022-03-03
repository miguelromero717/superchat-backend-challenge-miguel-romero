package de.superchat.backendchallenge.clients;

import de.superchat.backendchallenge.auth.payload.SignUpClientResponse;
import de.superchat.backendchallenge.shared.domain.Client;

import java.util.Optional;

public interface ClientService {

    Optional<SignUpClientResponse> signUpClient(Client client) throws Exception;

}
