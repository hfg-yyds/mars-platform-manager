package com.hacker.mars.application.api.monitor;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.hacker.mars.common.core.domain.AjaxResult;
import com.hacker.mars.common.enums.BusinessType;
import com.hacker.mars.domain.system.domain.SysLogininfor;
import com.hacker.mars.framework.web.service.SysPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hacker.mars.common.annotation.Log;
import com.hacker.mars.common.core.controller.BaseApi;
import com.hacker.mars.common.core.page.TableDataInfo;
import com.hacker.mars.common.utils.poi.ExcelUtil;
import com.hacker.mars.domain.system.service.ISysLoginInfoService;

/**
 * 系统访问记录
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/logininfor")
public class SysLoginInfoApi extends BaseApi {

    @Autowired
    private ISysLoginInfoService loginInfoService;

    @Autowired
    private SysPasswordService passwordService;

    @PreAuthorize("@ss.hasPermi('monitor:logininfor:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysLogininfor loginInfo) {
        startPage();
        List<SysLogininfor> list = loginInfoService.selectLogininforList(loginInfo);
        return getDataTable(list);
    }

    @Log(title = "登录日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysLogininfor loginInfo) {
        List<SysLogininfor> list = loginInfoService.selectLogininforList(loginInfo);
        ExcelUtil<SysLogininfor> util = new ExcelUtil<>(SysLogininfor.class);
        util.exportExcel(response, list, "登录日志");
    }

    @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{infoIds}")
    public AjaxResult remove(@PathVariable Long[] infoIds) {
        return toAjax(loginInfoService.deleteLogininforByIds(infoIds));
    }

    @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
    @Log(title = "登录日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    public AjaxResult clean() {
        loginInfoService.cleanLogininfor();
        return success();
    }

    @PreAuthorize("@ss.hasPermi('monitor:logininfor:unlock')")
    @Log(title = "账户解锁", businessType = BusinessType.OTHER)
    @GetMapping("/unlock/{userName}")
    public AjaxResult unlock(@PathVariable("userName") String userName) {
        passwordService.clearLoginRecordCache(userName);
        return success();
    }

}
