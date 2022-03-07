package de.superchat.backendchallenge.shared.services.queue.message;

import de.superchat.backendchallenge.config.queue.payload.Message;
import de.superchat.backendchallenge.config.queue.properties.QueueMessagesProperties;

public interface QueueMessagesSender {

    void sendMessage(QueueMessagesProperties queueMessagesProperties, Message message);

}
