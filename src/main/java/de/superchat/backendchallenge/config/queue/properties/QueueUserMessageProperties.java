package de.superchat.backendchallenge.config.queue.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "superchat.queue-user")
@Getter
@Setter
public class QueueUserMessageProperties {

    private String name;
    private String exchange;
    private String routingKey;

}
