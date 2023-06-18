package com.hacker.mars.infrastructure.dao.mapper;

import java.util.List;

import com.hacker.mars.domain.system.domain.SysLogininfor;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统访问日志情况信息 数据层
 *
 * @author ruoyi
 */
@Mapper
public interface SysLoginInfoMapper {

    /**
     * 新增系统登录日志
     *
     * @param loginInfo 访问日志对象
     */
    void insertLoginInfo(SysLogininfor loginInfo);

    /**
     * 查询系统登录日志集合
     *
     * @param loginInfo 访问日志对象
     * @return 登录记录集合
     */
    List<SysLogininfor> selectLoginInfoList(SysLogininfor loginInfo);

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    int deleteLoginInfoByIds(Long[] infoIds);

    /**
     * 清空系统登录日志
     *
     * @return 结果
     */
    int cleanLoginInfo();

}
