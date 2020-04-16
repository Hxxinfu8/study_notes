package org.hx.eureka.listener;

import org.hx.eureka.utils.ConsistentHashUtil;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRenewedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * eureka事件监听器
 * @author hx
 */
@Component
public class EurekaEventListener {

    /**
     * 服务下线事件
     * @param canceledEvent
     */
    @EventListener
    public void listen(EurekaInstanceCanceledEvent canceledEvent) {
        String serviceId = canceledEvent.getServerId();
        System.out.println("服务下线：" + serviceId);
        ConsistentHashUtil.removeNode(serviceId);
    }

    /**
     * 服务注册事件
     * @param registeredEvent
     */
    @EventListener
    public void listen(EurekaInstanceRegisteredEvent registeredEvent) {
        String serviceId = registeredEvent.getInstanceInfo().getInstanceId();
        System.out.println("服务注册: " + serviceId);
        ConsistentHashUtil.addRealNodes(serviceId);
    }

    /**
     * 服务续约事件
     * @param renewedEvent
     */
    @EventListener
    public void listen(EurekaInstanceRenewedEvent renewedEvent) {
    }
}
