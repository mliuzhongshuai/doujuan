package com.lingjing.doujuan.application;

import cn.dev33.satoken.stp.StpUtil;
import com.lingjing.doujuan.domain.AuthenticationService;
import com.lingjing.doujuan.domain.VerificationCodeService;
import com.lingjing.doujuan.infrastructure.sms.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 认证应用服务
 * 协调认证相关的用例，调用domain层服务
 */
@Service
@RequiredArgsConstructor
public class AuthenticationApplication {

    private final AuthenticationService authenticationService;
    private final VerificationCodeService verificationCodeService;
    private final SmsService smsService;

    /**
     * 手机号注册
     * 1. 调用认证服务注册用户
     * 2. 自动登录
     *
     * @param phone    手机号
     * @param code     验证码
     * @param password 密码（可选）
     * @return token
     */
    @Transactional
    public String register(String phone, String code, String password) {
        // 调用认证服务注册用户
        Integer userId = authenticationService.register(phone, code, password);

        // 自动登录
        StpUtil.login(userId);
        return StpUtil.getTokenValue();
    }

    /**
     * 发送短信验证码
     * 1. 生成验证码
     * 2. 存储到Redis
     * 3. 发送短信
     *
     * @param phone 手机号
     */
    public void sendSmsCode(String phone) {
        String code = verificationCodeService.generateAndStoreCode(phone);
        smsService.sendVerificationCode(phone, code);
    }

    /**
     * 手机验证码登录
     * 1. 调用认证服务验证登录信息
     * 2. 执行登录
     *
     * @param phone 手机号
     * @param code  验证码
     * @return token
     */
    public String loginCode(String phone, String code) {
        // 调用认证服务验证登录信息
        Integer userId = authenticationService.loginCode(phone, code);
        // 执行登录
        StpUtil.login(userId);
        return StpUtil.getTokenValue();
    }

    /**
     * 手机密码登录
     * 1. 调用认证服务验证登录信息
     * 2. 执行登录
     *
     * @param phone    手机号/用户名
     * @param password 密码
     * @return token
     */
    public String loginPassword(String phone, String password) {
        // 调用认证服务验证登录信息
        Integer userId = authenticationService.loginPassword(phone, password);

        // 执行登录
        StpUtil.login(userId);
        return StpUtil.getTokenValue();
    }
}
