package org.hx.dubbo.provider;

import org.hx.dubbo.api.ProviderService;

/**
 * @author Upoint0002
 */
public class ProviderServiceImpl implements ProviderService {
    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}
