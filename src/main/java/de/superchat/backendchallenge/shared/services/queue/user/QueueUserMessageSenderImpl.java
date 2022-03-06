package de.superchat.backendchallenge.shared.services.queue.user;

import de.superchat.backendchallenge.config.queue.payload.UserMessage;
import de.superchat.backendchallenge.shared.services.queue.QueueProperties;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class QueueUserMessageSenderImpl implements QueueUserMessageSender {

    private final AmqpTemplate amqpTemplate;

    @Autowired
    public QueueUserMessageSenderImpl(@Qualifier("userQueueTemplate") AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void sendMessage(QueueProperties config, UserMessage message) {
        amqpTemplate.convertAndSend(
                config.getExchange(),
                config.getRoutingKey(),
                message
        );
    }
}
