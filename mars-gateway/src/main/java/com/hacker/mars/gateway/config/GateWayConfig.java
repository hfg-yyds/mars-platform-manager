package com.hacker.mars.gateway.config;

import com.hacker.mars.gateway.handler.UrlRoutePredicateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author: 韩福贵
 * @date: 2023-05-13
 */
@Configuration
public class GateWayConfig {

    @Autowired
    private UrlProperties urlProperties;

    /**
     * 代码配置路由
     */
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        /*
//         * - id: admin # 路由的编号
//         *           uri: http://www.iocoder.cn # 路由到的目标地址
//         *           predicates: # 断言，作为路由的匹配条件，对应 RouteDefinition 数组
//         *             - Path=/blog
//         *           filters:
//         *             - StripPrefix=1
//         */
//
//        return builder.routes()
//                //根据Url进行断言
//                .route(predicateSpec ->
//                        predicateSpec
//                                .predicate(urlRoutePredicateFactory().apply(config -> config.setUrls(urlProperties.getAc())))
//                                .filters(f->f.stripPrefix(1))
//                                .uri("http://www.baidu.com")
//                                .id("ac-application"))
//                //根据路径进行断言
//                .route(predicateSpec ->
//                        predicateSpec
//                                .predicate(urlRoutePredicateFactory().apply(config -> config.setUrls(urlProperties.getClc())))
//                                .filters(f->f.stripPrefix(1))
//                                .uri("http://www.iocoder.cn")
//                                .id("clc-application"))  //localhost:8888/blog转发到https://www.iocoder.cn/blog
//                .build();
//    }


    /**
     * 根据Url进行断言工厂
     *
     * @return UrlRoutePredicateFactory
     */
    @Bean
    public UrlRoutePredicateFactory urlRoutePredicateFactory() {
        return new UrlRoutePredicateFactory();
    }

    public static List<String> toList(String[] str) {
        return new ArrayList<>(Arrays.asList(str));
    }

}
