package de.superchat.backendchallenge.external;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "superchat.external.crypto-api")
@Getter
@Setter
public class CryptoCurrencyProperties {

    private String keyHeader;
    private String key;
    private String url;
    private String pathAsset;

}
