package org.hx.gateway.filter;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.reactive.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
import org.springframework.cloud.client.loadbalancer.reactive.Request;
import org.springframework.cloud.client.loadbalancer.reactive.Response;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.gateway.support.DelegatingServiceInstance;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * websocket服务自定义负载均衡器
 * @author hx
 */
@Component
public class WebSocketBalanceFilter extends ReactiveLoadBalancerClientFilter {
    private final static String PREFIX = "lb";
    private final static String WS = "ws-service";
    private final static String TOKEN = "token";

    private final LoadBalancerClientFactory clientFactory;
    private LoadBalancerProperties properties;
    private final DiscoveryClient discoveryClient;

    public WebSocketBalanceFilter(LoadBalancerClientFactory clientFactory, LoadBalancerProperties properties, DiscoveryClient discoveryClient) {
        super(clientFactory, properties);
        this.clientFactory = clientFactory;
        this.properties = properties;
        this.discoveryClient = discoveryClient;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI url = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        String schemePrefix = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR);
        if (url != null && (PREFIX.equals(url.getScheme()) || PREFIX.equals(schemePrefix))) {
            return this.choose(exchange).doOnNext((response) -> {
                System.out.println("切入： " + response.getServer().getPort());
                if (!response.hasServer()) {
                    throw NotFoundException.create(this.properties.isUse404(), "Unable to find instance for " + url.getHost());
                } else {
                    URI uri = exchange.getRequest().getURI();
                    String overrideScheme = null;
                    if (schemePrefix != null) {
                        overrideScheme = url.getScheme();
                    }

                    DelegatingServiceInstance serviceInstance = new DelegatingServiceInstance((ServiceInstance)response.getServer(), overrideScheme);
                    System.out.println(response.getServer().getInstanceId());
                    URI requestUrl = this.reconstructURI(serviceInstance, uri);

                    exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, requestUrl);
                }
            }).then(chain.filter(exchange));
        } else {
            return chain.filter(exchange);
        }
    }

    private Mono<Response<ServiceInstance>> choose(ServerWebExchange exchange) {
        URI uri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        ReactorLoadBalancer<ServiceInstance> loadBalancer = this.clientFactory.getInstance(uri.getHost(), ReactorLoadBalancer.class, new Class[]{ServiceInstance.class});
        if (loadBalancer == null) {
            throw new NotFoundException("No loadbalancer available for " + uri.getHost());
        } else {
            String token = null;
            if (uri.getQuery() !=null && uri.getQuery().contains(TOKEN)) {
                token = uri.getQuery().split("=")[1];
            }
            if (WS.equals(uri.getAuthority().toLowerCase())) {
                List<ServiceInstance> instances = discoveryClient.getInstances(uri.getAuthority());
                Response<ServiceInstance> response = null;
                for (ServiceInstance instance : instances) {
                    if ("127.0.0.1:ws-service:8776".equals(instance.getInstanceId())) {
                        response = new DefaultResponse(instance);
                        break;
                    }
                }
                return Mono.just(response);
            }
            return loadBalancer.choose(this.createRequest(uri));
        }
    }

    private Request createRequest(URI uri) {
        return ReactiveLoadBalancer.REQUEST;
    }
}
