package de.superchat.backendchallenge.messages;

import de.superchat.backendchallenge.shared.enums.Channels;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageResponse {

    private String message;
    private String clientName;
    private String contactName;
    private Channels channel;
    private LocalDateTime dateMessage;

}
