package de.superchat.backendchallenge.clients;

import de.superchat.backendchallenge.auth.payload.SignUpClientResponse;
import de.superchat.backendchallenge.auth.services.UserService;
import de.superchat.backendchallenge.shared.domain.Client;
import de.superchat.backendchallenge.shared.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Value("${superchat.app.user-password}")
    private String newPassword;

    private final ClientRepository clientRepository;

    private final UserService userService;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, UserService userService) {
        this.clientRepository = clientRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Optional<SignUpClientResponse> signUpClient(Client client) throws Exception {
        logger.info("Save client service");

        Client clientOpt = Optional.of(clientRepository.save(client)).orElseThrow();

        Optional<User> user = userService.createUserFromClient(client.getName(), client.getEmail());

        if (user.isEmpty())
            throw new Exception(); // TODO Improve exception handling

        var signUpClientResponse = new SignUpClientResponse(
                "Client Registered!! These are your credentials to access",
                user.get().getEmail(),
                newPassword);

        return Optional.ofNullable(Optional.of(signUpClientResponse).orElseThrow(Exception::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> getClientById(Long clientId) throws Exception {
        logger.info("Get Client By Id");
        return clientRepository.findById(clientId);
    }
}
