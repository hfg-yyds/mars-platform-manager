package com.hacker.mars.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <p>
 *  火星平台网关
 * </p>
 *
 * @author: 韩福贵
 * @date: 2023-04-24
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudGateWay {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGateWay.class, args);
    }

}
