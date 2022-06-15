package com.wisdge.cloud.configurer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Slf4j
@Configuration
public class TomcatContainerConfigurer {

    @Value("${tldSkipPatterns:}")
    private String[] tldSkipPatterns;

    @Bean
    public BeanPostProcessor TomcatContainerPostProcessor() {
        List<String> notEmptyTldSkipPatterns = Arrays.stream(tldSkipPatterns)
                .filter(tldSkipPattern -> !tldSkipPattern.trim().isEmpty())
                .collect(toList());


        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (beanName.equals("tomcatServletWebServerFactory") && bean instanceof TomcatServletWebServerFactory) {
                    TomcatServletWebServerFactory factory = (TomcatServletWebServerFactory) bean;
                    if (! notEmptyTldSkipPatterns.isEmpty()) {
                        factory.addTldSkipPatterns(notEmptyTldSkipPatterns.toArray(new String[0]));
                    }
                }
                return bean;
            }
        };
    }
}
