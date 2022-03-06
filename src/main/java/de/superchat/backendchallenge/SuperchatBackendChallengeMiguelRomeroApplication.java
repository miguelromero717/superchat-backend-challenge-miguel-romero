package de.superchat.backendchallenge;

import de.superchat.backendchallenge.config.queue.properties.QueueUserMessageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        QueueUserMessageProperties.class
})

public class SuperchatBackendChallengeMiguelRomeroApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperchatBackendChallengeMiguelRomeroApplication.class, args);
    }

}
