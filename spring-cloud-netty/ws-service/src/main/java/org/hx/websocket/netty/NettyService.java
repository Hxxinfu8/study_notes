package org.hx.websocket.netty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hx
 */
@Component
public class NettyService {
    @Value("${netty-websocket.port}")
    private Integer port;

    @Bean
    private void startService() {
        new ThreadPoolExecutor(1, 24, 60, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(100)).execute(new NettyThread(port));
    }

}
