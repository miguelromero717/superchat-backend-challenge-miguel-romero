package de.superchat.backendchallenge.messages;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessagesController {

    private final MessagesService messagesService;

    @Autowired
    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @Operation(summary = "Send message to one Contact from Client")
    @PostMapping("/clients/{clientId}/contact/{contactId}")
    public ResponseEntity<?> sendMessageToContact(@PathVariable Long clientId, @PathVariable Long contactId,
            @RequestBody @Validated MessagesRequest messagesRequest) throws Exception {

        messagesService.sendMessageToContact(clientId, contactId, messagesRequest.getChannel());

        return ResponseEntity.ok("Message Sent Correctly");
    }

    @Operation(summary = "Get List of messages between client and contact")
    @GetMapping("/clients/{clientId}/contact/{contactId}")
    public ResponseEntity<List<?>> getMessagesWithContact(@PathVariable Long clientId,
                                                          @PathVariable Long contactId) throws Exception {

        return ResponseEntity.ok(messagesService.getMessagesClientContactByDate(
                clientId,
                contactId
        ));
    }

}
