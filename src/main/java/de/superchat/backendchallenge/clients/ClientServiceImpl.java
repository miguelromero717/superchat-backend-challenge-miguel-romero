package de.superchat.backendchallenge.clients;

import de.superchat.backendchallenge.shared.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public Optional<Client> saveClient(Client client) throws Exception {
        return Optional.ofNullable(
                Optional.of(clientRepository.save(client))
                        .orElseThrow(Exception::new));
    }
}
