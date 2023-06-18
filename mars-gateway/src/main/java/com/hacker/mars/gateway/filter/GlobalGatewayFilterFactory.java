package com.hacker.mars.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * <p>
 *
 * </p>
 *
 * @author: 韩福贵
 * @date: 2023-05-13
 */
@Slf4j
@Service
public class GlobalGatewayFilterFactory extends AbstractGatewayFilterFactory<GlobalGatewayFilterFactory.Config> {

    public GlobalGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                URI uri = exchange.getRequest().getURI();
                log.info("请求路径"+uri.toString());
                return chain.filter(exchange);
            }

            @Override
            public String toString() {
                return super.toString();
            }
        };
    }

    public static class Config {

        private String request;

        public String getRequest() {
            return request;
        }

        public void setRequest(String request) {
            this.request = request;
        }

    }

}
