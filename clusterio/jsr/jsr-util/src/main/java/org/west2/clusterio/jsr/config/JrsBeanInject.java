package org.west2.clusterio.jsr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 86189
 */
@Component
public class JrsBeanInject {
    public JrsBeanInject(@Autowired(required = false) CodingConfig codingConfig){
        if(codingConfig != null) {
            JrsManager.setConfig(codingConfig);
        }
    }
}
