package de.superchat.backendchallenge.external.webhook;

import de.superchat.backendchallenge.messages.MessagesService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/webhook")
public class WebhookController {

    private final MessagesService messagesService;

    @Autowired
    public WebhookController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @Operation(summary = "Public Webhook that receive messages from external systems")
    @PostMapping
    public ResponseEntity<String> sendMessageWebhook(@RequestBody @Validated WebhookRequest webhookRequest) throws Exception {
        messagesService.sendMessageFromWebhook(webhookRequest);
        return ResponseEntity.ok("Message Sent");
    }

}
