package com.hacker.mars.gateway.refresh;

import com.ctrip.framework.apollo.enums.PropertyChangeType;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.hacker.mars.gateway.constant.GateWayConst;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * <p>
 *  Gateway参数刷新
 * </p>
 *
 * @author: 韩福贵
 * @date: 2023-04-24
 */
@Slf4j
@Service
public class GatewayPropertiesRefresher implements ApplicationContextAware,ApplicationEventPublisherAware {

    /**
     * 应用上下文
     */
    private ApplicationContext applicationContext;

    /**
     * 实践发布
     */
    private ApplicationEventPublisher publisher;

    @Autowired
    private GatewayProperties gatewayProperties;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationEventPublisher(@NonNull ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @ApolloConfigChangeListener(interestedKeyPrefixes = GateWayConst.GATE_WAY)
    public void onChange(ConfigChangeEvent changeEvent) {
        refreshGatewayProperties(changeEvent);
    }

    @ApolloConfigChangeListener(interestedKeyPrefixes = GateWayConst.GATE_WAY_URLS)
    public void onChangeUrls(ConfigChangeEvent changeEvent) {
        //刷新路由
        this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
        this.refreshGatewayRouteDefinition();
    }

    /**
     * 刷新{@link org.springframework.cloud.gateway.config.PropertiesRouteDefinitionLocator}中定义的routes
     * @param changeEvent Apollo配置事件
     */
    private void refreshGatewayProperties(ConfigChangeEvent changeEvent) {
        log.info("Refreshing GatewayProperties!");
        preDestroyGatewayProperties(changeEvent);
        this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
        this.refreshGatewayRouteDefinition();
        log.info("GatewayProperties refreshed!");
    }

    /***
     * GatewayProperties没有@PreDestroy和destroy方法
     * org.springframework.cloud.context.properties.ConfigurationPropertiesRebinder#rebind(java.lang.String)中destroyBean时不会销毁当前对象
     * 如果把spring.cloud.gateway.前缀的配置项全部删除（例如需要动态删除最后一个路由的场景），initializeBean时也无法创建新的bean，则return当前bean
     * 若仍保留有spring.cloud.gateway.routes[n]或spring.cloud.gateway.default-filters[n]等配置，initializeBean时会注入新的属性替换已有的bean
     * 这个方法提供了类似@PreDestroy的操作，根据配置文件的实际情况把org.springframework.cloud.gateway.config.GatewayProperties#routes
     * 和org.springframework.cloud.gateway.config.GatewayProperties#defaultFilters两个集合清空
     *
     * @param
     * @return void
     * @author ksewen
     * @date 2019/5/21 2:13 PM
     */
    private synchronized void preDestroyGatewayProperties(ConfigChangeEvent changeEvent) {
        log.info("Pre Destroy GatewayProperties!");
        final boolean needClearRoutes = this.checkNeedClear(changeEvent, GateWayConst.ID_PATTERN, this.gatewayProperties.getRoutes().size());
        if (needClearRoutes) {
            this.gatewayProperties.setRoutes(new ArrayList<>());
        }
        final boolean needClearDefaultFilters = this.checkNeedClear(changeEvent, GateWayConst.DEFAULT_FILTER_PATTERN, this.gatewayProperties.getDefaultFilters().size());
        if (needClearDefaultFilters) {
            this.gatewayProperties.setDefaultFilters(new ArrayList<>());
        }
        log.info("Pre Destroy GatewayProperties finished!");
    }

    private void refreshGatewayRouteDefinition() {
        log.info("Refreshing Gateway RouteDefinition!");
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        log.info("Gateway RouteDefinition refreshed!");
    }

    /**
     * 根据changeEvent和定义的pattern匹配key，如果所有对应PropertyChangeType为DELETED则需要清空GatewayProperties里相关集合
     *
     * @param changeEvent
     * @param pattern
     * @param existSize
     * @return boolean
     * @author ksewen
     * @date 2019/5/23 2:18 PM
     */
    private boolean checkNeedClear(ConfigChangeEvent changeEvent, String pattern, int existSize) {
        return changeEvent.changedKeys().stream().filter(key -> key.matches(pattern))
                .filter(key -> {
                    ConfigChange change = changeEvent.getChange(key);
                    return PropertyChangeType.DELETED.equals(change.getChangeType());
                }).count() == existSize;
    }

}

