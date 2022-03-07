package de.superchat.backendchallenge.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/messages")
public class MessagesController {

    private final MessagesService messagesService;

    @Autowired
    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @PostMapping("/clients/{clientId}/contact/{contactId}")
    public ResponseEntity<?> sendMessageToContact(@PathVariable Long clientId, @PathVariable Long contactId,
            @RequestBody @Validated MessagesRequest messagesRequest) throws Exception {

        messagesService.sendMessageToContact(clientId, contactId, messagesRequest.getChannel());

        return ResponseEntity.ok("Message Sent Correctly");
    }

}
