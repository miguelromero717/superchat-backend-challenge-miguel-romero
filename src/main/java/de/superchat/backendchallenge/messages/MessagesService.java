package de.superchat.backendchallenge.messages;

import de.superchat.backendchallenge.shared.domain.Message;
import de.superchat.backendchallenge.shared.enums.Channels;

import java.util.List;

public interface MessagesService {

    void sendMessageToContact(Long senderId, Long recipientId, Channels channel) throws Exception;

    List<Message> getMessagesClientContactByDate(Long senderId, Long recipientId) throws Exception;

}
