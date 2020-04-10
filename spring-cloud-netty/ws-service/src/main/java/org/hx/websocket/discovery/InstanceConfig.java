package org.hx.websocket.discovery;

import com.netflix.appinfo.MyDataCenterInstanceConfig;
import org.hx.websocket.utils.BeanUtil;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Query;
import java.lang.management.ManagementFactory;
import java.util.Set;

/**
 * @author hx
 */
public class InstanceConfig extends MyDataCenterInstanceConfig {

    @Override
    public String getHostName(boolean refresh) {
        return BeanUtil.getBean(InstanceProperties.class).getHost();
    }

    @Override
    public int getNonSecurePort(){
        int tomcatPort;
        try {
            MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
            Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
                    Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));

            tomcatPort = Integer.valueOf(objectNames.iterator().next().getKeyProperty("port"));
        }catch (Exception e){
            return super.getNonSecurePort();
        }
        return tomcatPort;
    }
}
