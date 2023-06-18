package com.hacker.mars.param.application.api;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * <p>
 *
 * </p>
 *
 * @author: 韩福贵
 * @date: 2023-06-18
 */
@Slf4j
@Api(value = "参数响应式编程Api")
@RestController
@RequestMapping("/param")
public class ParamReactiveApi {

    @GetMapping("/hello")
    public Mono<String> hello() {
        log.info("方法 hello 被调用了");
        return Mono.just("name");
    }

}
