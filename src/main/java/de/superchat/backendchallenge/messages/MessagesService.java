package de.superchat.backendchallenge.messages;

import de.superchat.backendchallenge.shared.enums.Channels;

public interface MessagesService {

    void sendMessageToContact(Long senderId, Long recipientId, Channels channel) throws Exception;

}
