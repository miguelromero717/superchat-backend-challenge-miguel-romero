package de.superchat.backendchallenge.auth.controllers;

import de.superchat.backendchallenge.auth.payload.SignUpClientRequest;
import de.superchat.backendchallenge.clients.ClientService;
import de.superchat.backendchallenge.shared.domain.Client;
import de.superchat.backendchallenge.shared.enums.ClientStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/clients")
public class SignUpClientsController {

    private static final Logger logger = LoggerFactory.getLogger(SignUpClientsController.class);

    private final ClientService clientService;

    @Autowired
    public SignUpClientsController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUpClient (@RequestBody @Validated SignUpClientRequest signUpClientRequest) throws Exception {
        logger.info("Sign Up Client endpoint");

        Client client = new Client();
        client.setName(signUpClientRequest.getName());
        client.setStatus(ClientStatus.active);

        return ResponseEntity.ok(clientService.saveClient(client));
    }

}
