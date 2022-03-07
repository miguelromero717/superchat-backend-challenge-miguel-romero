package de.superchat.backendchallenge.messages;

import de.superchat.backendchallenge.config.queue.payload.Message;
import de.superchat.backendchallenge.shared.enums.Channels;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SMSMessageBuilder implements MessagesBuilder{

    @Override
    public Message buildMessage(Object... values) throws Exception {
        return new Message(
                values[0].toString(),
                Channels.valueOf(values[1].toString()),
                values[2].toString(),
                Long.parseLong(values[3].toString()),
                Long.parseLong(values[4].toString()),
                LocalDateTime.now()
        );
    }
}
