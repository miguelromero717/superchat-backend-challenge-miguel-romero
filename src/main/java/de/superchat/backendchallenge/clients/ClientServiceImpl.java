package de.superchat.backendchallenge.clients;

import de.superchat.backendchallenge.auth.payload.SignUpClientResponse;
import de.superchat.backendchallenge.config.queue.payload.UserMessage;
import de.superchat.backendchallenge.config.queue.properties.QueueUserMessageProperties;
import de.superchat.backendchallenge.shared.domain.Client;
import de.superchat.backendchallenge.shared.exceptions.ClientException;
import de.superchat.backendchallenge.shared.services.queue.QueueProperties;
import de.superchat.backendchallenge.shared.services.queue.user.QueueUserMessageSender;
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
    private final QueueUserMessageSender queueUserMessageSender;
    private final QueueUserMessageProperties queueUserMessageProperties;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, QueueUserMessageSender queueUserMessageSender,
                             QueueUserMessageProperties queueUserMessageProperties) {
        this.clientRepository = clientRepository;
        this.queueUserMessageSender = queueUserMessageSender;
        this.queueUserMessageProperties = queueUserMessageProperties;
    }

    @Override
    @Transactional
    public Optional<SignUpClientResponse> signUpClient(Client client) throws Exception {
        logger.info("Save client service");

        Client clientOpt = Optional.of(
                clientRepository.save(client))
                .orElseThrow(() -> new ClientException("Error registering new Client"));

        queueUserMessageSender.sendMessage(
                new QueueProperties(queueUserMessageProperties.getExchange(), queueUserMessageProperties.getRoutingKey()),
                new UserMessage(clientOpt.getName(), clientOpt.getEmail())
        );

        var signUpClientResponse = new SignUpClientResponse(
                "Client Registered!! These are your credentials to access",
                clientOpt.getEmail(),
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
