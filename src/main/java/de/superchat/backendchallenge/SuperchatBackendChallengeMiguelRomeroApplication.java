package de.superchat.backendchallenge;

import de.superchat.backendchallenge.config.queue.properties.QueueMessagesProperties;
import de.superchat.backendchallenge.config.queue.properties.QueueUserMessageProperties;
import de.superchat.backendchallenge.external.CryptoCurrencyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        QueueUserMessageProperties.class,
        QueueMessagesProperties.class,
        CryptoCurrencyProperties.class
})
public class SuperchatBackendChallengeMiguelRomeroApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperchatBackendChallengeMiguelRomeroApplication.class, args);
    }

}
