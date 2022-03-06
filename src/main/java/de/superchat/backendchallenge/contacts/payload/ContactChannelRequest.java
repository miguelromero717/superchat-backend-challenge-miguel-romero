package de.superchat.backendchallenge.contacts.payload;

import de.superchat.backendchallenge.shared.enums.Channels;
import de.superchat.backendchallenge.shared.enums.ContactChannelStatus;
import lombok.Data;

@Data
public class ContactChannelRequest {

    private Channels channel;
    private String value;
    private ContactChannelStatus status;

}
