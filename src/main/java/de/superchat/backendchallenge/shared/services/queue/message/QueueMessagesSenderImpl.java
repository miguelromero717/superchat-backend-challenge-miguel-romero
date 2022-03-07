package de.superchat.backendchallenge.shared.services.queue.message;

import de.superchat.backendchallenge.config.queue.payload.Message;
import de.superchat.backendchallenge.config.queue.properties.QueueMessagesProperties;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class QueueMessagesSenderImpl implements QueueMessagesSender {

    private final AmqpTemplate amqpTemplate;

    @Autowired
    public QueueMessagesSenderImpl(@Qualifier("messageQueueTemplate") AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void sendMessage(QueueMessagesProperties config, Message message) {
        amqpTemplate.convertAndSend(
                config.getExchange(),
                config.getRoutingKey(),
                message
        );
    }

}
