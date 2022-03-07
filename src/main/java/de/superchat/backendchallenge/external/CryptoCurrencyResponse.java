package de.superchat.backendchallenge.external;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode
public class CryptoCurrencyResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String asset_id;
    private String name;
    private Double price_usd;

}
