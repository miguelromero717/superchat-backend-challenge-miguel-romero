package de.superchat.backendchallenge.config.queue;

import de.superchat.backendchallenge.config.queue.properties.QueueUserMessageProperties;
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
public class QueueUserConfig {

    private final QueueUserMessageProperties properties;

    @Autowired
    public QueueUserConfig(QueueUserMessageProperties queueUserMessageProperties) {
        this.properties = queueUserMessageProperties;
    }

    @Bean
    Queue queueUser() {
        return new Queue(properties.getName(), false);
    }

    @Bean
    DirectExchange exchangeUser() {
        return new DirectExchange(properties.getExchange());
    }

    @Bean
    Binding bindingUser(Queue queueUser, DirectExchange exchangeUser) {
        return BindingBuilder.bind(queueUser).to(exchangeUser).with(properties.getRoutingKey());
    }

    @Bean
    @Qualifier("userQueueConverter")
    public MessageConverter jsonUserMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    @Qualifier("userQueueTemplate")
    public AmqpTemplate queueUserTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonUserMessageConverter());
        return rabbitTemplate;
    }


}
