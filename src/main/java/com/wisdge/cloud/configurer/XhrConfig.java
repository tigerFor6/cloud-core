package com.wisdge.cloud.configurer;

import com.wisdge.dataservice.xhr.ProxyConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "xhr")
public class XhrConfig {
    private int maxConnection = 300;
    private int maxPerRoute = 100;
    private int maxRetryCount = 3;
    private int requestTimeout = 600000;
    private int connectTimeout = 60000;
    private int socketTimeout = 600000;
    private String protocol = "TLSv1.2";
    private ProxyConfig proxyConfig;
}
