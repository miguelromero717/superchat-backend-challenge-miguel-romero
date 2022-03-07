package de.superchat.backendchallenge.messages;

import de.superchat.backendchallenge.config.queue.payload.Message;

public interface MessagesBuilder {

    Message buildMessage(Object... values) throws Exception;

}
