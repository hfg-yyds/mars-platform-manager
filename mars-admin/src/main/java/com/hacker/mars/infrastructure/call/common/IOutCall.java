package com.hacker.mars.infrastructure.call.common;

/**
 * <p>
 * 所有Post请求外调都需要实现该接口
 * </p>
 *
 * @author: 韩福贵
 * @date: 2023-05-21
 */
public interface IOutCall {

    /**
     * 设置外调流水
     *
     * @return 流水
     */
    default String getRequestSeq() {
        return "";
    }

}
