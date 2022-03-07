package de.superchat.backendchallenge.config.queue.payload;

import de.superchat.backendchallenge.shared.enums.Channels;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private String message;
    private Channels channel;
    private String channelValue;
    private Long senderId;
    private Long recipientId;
    private LocalDateTime timeSend;

    @Override
    public String toString() {
        return message + ", " + channel + ", " + channelValue + ", " + senderId + ", " + recipientId;
    }
}
