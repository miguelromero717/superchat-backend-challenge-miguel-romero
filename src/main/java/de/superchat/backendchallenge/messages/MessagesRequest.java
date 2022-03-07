package de.superchat.backendchallenge.messages;

import de.superchat.backendchallenge.shared.enums.Channels;
import lombok.Data;

@Data
public class MessagesRequest {

    private Channels channel;

}
