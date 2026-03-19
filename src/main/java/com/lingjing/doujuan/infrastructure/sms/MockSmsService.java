package com.lingjing.doujuan.infrastructure.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 模拟短信服务实现（用于开发环境）
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "aliyun.sms", name = "enabled", havingValue = "false", matchIfMissing = true)
public class MockSmsService implements SmsService {

    @Override
    public void sendVerificationCode(String phone, String code) {
        log.info("Mock SMS: Sending verification code '{}' to phone '{}'", code, phone);
        // 在开发环境中，直接将验证码打印到日志
    }
}
