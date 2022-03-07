package de.superchat.backendchallenge.config.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.superchat.backendchallenge.config.queue.properties.QueueMessagesProperties;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueMessageConfig {

    private final QueueMessagesProperties properties;

    @Autowired
    public QueueMessageConfig(QueueMessagesProperties properties) {
        this.properties = properties;
    }

    @Bean
    Queue queueMessage() {
        return new Queue(properties.getName(), false);
    }

    @Bean
    DirectExchange exchangeMessage() {
        return new DirectExchange(properties.getExchange());
    }

    @Bean
    Binding bindingMessage(Queue queueMessage, DirectExchange exchangeMessage) {
        return BindingBuilder.bind(queueMessage).to(exchangeMessage).with(properties.getRoutingKey());
    }

    @Bean
    @Qualifier("messageQueueConverter")
    public MessageConverter jsonMessagesConverter() {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    @Qualifier("messageQueueTemplate")
    public AmqpTemplate queueMessagesTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessagesConverter());
        return rabbitTemplate;
    }
}
