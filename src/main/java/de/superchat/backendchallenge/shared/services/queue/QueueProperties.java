package de.superchat.backendchallenge.shared.services.queue;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QueueProperties {

    private String exchange;
    private String routingKey;

}
