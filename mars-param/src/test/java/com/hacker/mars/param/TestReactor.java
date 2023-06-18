package com.hacker.mars.param;

import org.junit.Test;
import reactor.core.publisher.Flux;

/**
 * <p>
 *
 * </p>
 *
 * @author: 韩福贵
 * @date: 2023-06-18
 */
public class TestReactor {

    @Test
    public void test1() {
        //创建Flux序列，并声明数据流，
        Flux<Integer> integerFlux = Flux.just(1, 2, 3, 4);
        integerFlux.subscribe(System.out::println);
    }

}
