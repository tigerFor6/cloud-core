package com.wisdge.cloud.configurer;

import com.wisdge.commons.es.ESClientFactory;
import com.wisdge.commons.mail.MailSender;
import com.wisdge.commons.redis.RedisTemplate;
import com.wisdge.dataservice.xhr.XHRPoolService;
import com.wisdge.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ComponentConfigurer implements CommandLineRunner, DisposableBean {
    @Value("${spring.redis.namespace}")
    private String redisNamespace;
    @Autowired
    private SnowflakeConfig snowflakeConfig;
    @Autowired
    private XhrConfig xhrConfig;

    private XHRPoolService xhrService;
    private ESClientFactory esClientFactory;

    @Bean
    @ConfigurationProperties(prefix = "mail-sender")
    public MailSender mailSender() {
        return new MailSender();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        DelegatingPasswordEncoder delegatingPasswordEncoder = (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // 设置defaultPasswordEncoderForMatches为NoOpPasswordEncoder
        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(NoOpPasswordEncoder.getInstance());
        return delegatingPasswordEncoder;
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate(redisNamespace);
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public SnowflakeIdWorker snowflakeIdWorker() {
        return new SnowflakeIdWorker(snowflakeConfig.getWorkId(), snowflakeConfig.getCenterId());
    }

    @Bean
    public XHRPoolService xhrPoolService() {
        log.debug("Create XHRPoolService instance");
        xhrService = new XHRPoolService(xhrConfig.getMaxConnection(),
                xhrConfig.getMaxPerRoute(),
                xhrConfig.getMaxRetryCount(),
                xhrConfig.getRequestTimeout(),
                xhrConfig.getConnectTimeout(),
                xhrConfig.getSocketTimeout(),
                xhrConfig.getProtocol(),
                xhrConfig.getProxyConfig());
        return xhrService;
    }

    @Bean
    @ConfigurationProperties(prefix = "es-client-factory")
    public ESClientFactory esClientFactory() {
        esClientFactory = new ESClientFactory();
        return esClientFactory;
    }

    @Override
    public void destroy() throws Exception {
        if (xhrService != null) {
            log.debug("Destroy XHRPoolService instance");
            xhrService.destroy();
        }
        if (esClientFactory != null) {
            log.debug("Destroy ESClientFactory instance");
            esClientFactory.close();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        //初始化es
        esClientFactory.init();
    }
}
