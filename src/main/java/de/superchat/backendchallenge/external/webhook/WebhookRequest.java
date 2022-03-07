package de.superchat.backendchallenge.external.webhook;

import de.superchat.backendchallenge.shared.enums.Channels;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WebhookRequest {

    @NotNull
    private Long senderId;

    @NotNull
    private Long recipientId;

    @NotNull
    private Channels channel;

    @NotNull
    private String channelValue;

    @NotNull
    private String message;

}
