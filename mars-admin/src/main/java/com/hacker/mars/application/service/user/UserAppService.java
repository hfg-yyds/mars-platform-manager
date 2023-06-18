package com.hacker.mars.application.service.user;

import com.hacker.mars.common.utils.sign.Base64;
import com.hacker.mars.common.core.domain.AjaxResult;
import com.hacker.mars.common.utils.uuid.IdUtils;
import com.hacker.mars.domain.system.service.ISysConfigService;
import com.hacker.mars.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * <p>
 * 用户服务-登录、注册
 * </p>
 *
 * @author: 韩福贵
 * @date: 2023-05-20
 */
@Slf4j
@Service
public class UserAppService {

    @Autowired
    private UserService userService;

    @Autowired
    private ISysConfigService configService;

    /**
     * 生成登录验证码服务
     *
     * @return AjaxResult
     */
    public AjaxResult getLoginCode() {
        AjaxResult ajax = AjaxResult.success();

        //查询验证码开关是否打开
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        ajax.put("captchaEnabled", captchaEnabled);
        if (!captchaEnabled) {
            return ajax;
        }

        //生成验证码并写入到缓存中去
        String uuid = IdUtils.simpleUUID();
        BufferedImage image = userService.getBufferedImage(uuid);

        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            assert image != null;
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return AjaxResult.error(e.getMessage());
        }
        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }


}
