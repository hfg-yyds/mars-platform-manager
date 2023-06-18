package com.hacker.mars.domain.user.service;

import com.google.code.kaptcha.Producer;
import com.hacker.mars.common.constant.CacheConstants;
import com.hacker.mars.common.constant.Constants;
import com.hacker.mars.common.config.RuoYiConfig;
import com.hacker.mars.common.core.redis.RedisCache;
import com.hacker.mars.domain.user.constanst.UserConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户中心服务
 * </p>
 *
 * @author: 韩福贵
 * @date: 2023-05-20
 */
@Slf4j
@Service
public class UserService {

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;

    /**
     * 生成严重码并写入到缓存中去
     *
     * @param uuid uuid
     * @return BufferedImage
     */
    public BufferedImage getBufferedImage(String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        // 生成验证码
        String capStr, code = null;
        BufferedImage image = null;
        String captchaType = RuoYiConfig.getCaptchaType();
        if (UserConst.MATH_CAPTCHA.equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if (UserConst.CHAR_CAPTCHA.equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }
        //验证码写入缓存
        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        return image;
    }

}
