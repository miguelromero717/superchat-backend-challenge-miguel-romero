package de.superchat.backendchallenge.shared.services.queue.user;

import de.superchat.backendchallenge.config.queue.payload.UserMessage;
import de.superchat.backendchallenge.shared.services.queue.QueueProperties;

public interface QueueUserMessageSender {

    void sendMessage(QueueProperties config, UserMessage message);

}
