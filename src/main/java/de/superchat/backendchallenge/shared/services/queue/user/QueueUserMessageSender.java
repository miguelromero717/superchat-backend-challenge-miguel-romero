package de.superchat.backendchallenge.shared.services.queue.user;

import de.superchat.backendchallenge.config.queue.payload.UserMessage;
import de.superchat.backendchallenge.config.queue.properties.QueueUserMessageProperties;

public interface QueueUserMessageSender {

    void sendMessage(QueueUserMessageProperties config, UserMessage message);

}
