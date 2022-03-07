package de.superchat.backendchallenge.external;

public interface CryptoCurrencyProvider {

    void initCryptoProperties(String url, String headerKey, String key) throws Exception;

    Double getCryptoCurrencyPrice(String assetId) throws Exception;

}
