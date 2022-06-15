package com.wisdge.cloud.configurer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@EnableConfigurationProperties(value = RsaKeysConfig.class)
@ConfigurationProperties(prefix = "rsa")
public class RsaKeysConfig {

    /**
     * RSA private key
     */
    private String privateKey;
    /**
     * RSA public key
     */
    private String publicKey;
}
