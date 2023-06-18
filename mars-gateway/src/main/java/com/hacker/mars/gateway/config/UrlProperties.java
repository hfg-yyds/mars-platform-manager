package com.hacker.mars.gateway.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author: 韩福贵
 * @date: 2023-05-20
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties("mars.gateway.urls")
public class UrlProperties {

    /**
     * 网关参数配置
     */
    private List<String> ac;

    /**
     * 网关参数配置
     */
    private List<String> clc;

}
