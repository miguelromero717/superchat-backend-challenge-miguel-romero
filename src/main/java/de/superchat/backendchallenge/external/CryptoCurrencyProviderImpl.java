package de.superchat.backendchallenge.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CryptoCurrencyProviderImpl implements CryptoCurrencyProvider {

    private final RestTemplate restTemplate;

    private String url;
    private String headerKey;
    private String key;

    @Autowired
    public CryptoCurrencyProviderImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void initCryptoProperties(String url, String headerKey, String key) throws Exception {
        this.url = url;
        this.headerKey = headerKey;
        this.key = key;
    }

    @Override
    public Double getCryptoCurrencyPrice(String assetId) throws Exception {
        HttpHeaders headers = buildHeaders();

        HttpEntity<?> requestEntity = new HttpEntity<>(null, headers);

        final String urlAssets = this.url + assetId;

        ResponseEntity<CryptoCurrencyResponse[]> result = restTemplate.exchange(urlAssets, HttpMethod.GET, requestEntity, CryptoCurrencyResponse[].class);

        return result.getBody()[0].getPrice_usd();
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(this.headerKey, this.key);

        return headers;
    }
}
