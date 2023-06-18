package com.hacker.mars.gateway.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * <p>
 * 根据Url实现路径映射
 * </p>
 *
 * @author: 韩福贵
 * @date: 2023-05-13
 */
@Slf4j
public class UrlRoutePredicateFactory extends AbstractRoutePredicateFactory<UrlRoutePredicateFactory.Config> {

    /**
     * URL key
     */
    public static final String URL = "urls";

    public UrlRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(URL);
    }

    @Override
    public ShortcutType shortcutType() {
        return ShortcutType.GATHER_LIST;
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        if (log.isDebugEnabled()) {
            log.debug("日志:{}",URL);
        }
        List<String> urls = config.getUrls().stream().distinct().collect(Collectors.toList());
        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                URI uri = serverWebExchange.getRequest().getURI();
                return urls.contains(uri.getPath());
            }
            @Override
            public String toString() {
                return String.format("Url: %s", config.getUrls());
            }
        };
    }

    public static class Config {
        /**
         * 根据Url进行路由
         */
        private List<String> urls = new ArrayList<>();

        public List<String> getUrls() {
            return urls;
        }

        public void setUrls(List<String> urls) {
            this.urls = urls;
        }
    }

}


