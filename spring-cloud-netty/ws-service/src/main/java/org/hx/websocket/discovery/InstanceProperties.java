package org.hx.websocket.discovery;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author hx
 */
@Data
@Component
@ConfigurationProperties(prefix = InstanceProperties.PREFIX)
public class InstanceProperties {
    public static final String PREFIX = "netty-websocket.discovery.client";

    private String name;
    private String host;
    private Integer port;

}
