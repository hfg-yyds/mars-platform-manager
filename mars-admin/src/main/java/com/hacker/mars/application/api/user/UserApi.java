package com.hacker.mars.application.api.user;

import com.hacker.mars.application.service.user.UserAppService;
import com.hacker.mars.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码操作处理
 *
 * @author ruoyi
 */
@Api("验证码操作Api")
@RestController
public class UserApi {

    @Autowired
    private UserAppService userAppService;

    @ApiOperation(value = "生成登录验证码接口")
    @GetMapping("/captchaImage")
    public AjaxResult getLoginCode() {
        return userAppService.getLoginCode();
    }

}
