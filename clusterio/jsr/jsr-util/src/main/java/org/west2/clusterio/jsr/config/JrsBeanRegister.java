package org.west2.clusterio.jsr.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Haechi
 */
@Component
public class JrsBeanRegister {
    @Bean
    @ConfigurationProperties(prefix = "jsr.coding")
    public CodingConfig getSaTokenConfig() {
        return new CodingConfig();
    }
}
