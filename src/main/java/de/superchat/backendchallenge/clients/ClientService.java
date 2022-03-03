package de.superchat.backendchallenge.clients;

import de.superchat.backendchallenge.shared.domain.Client;

import java.util.Optional;

public interface ClientService {

    Optional<Client> saveClient(Client client) throws Exception;

}
