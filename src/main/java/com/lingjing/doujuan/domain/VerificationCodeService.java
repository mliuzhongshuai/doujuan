package com.lingjing.doujuan.domain;

import com.lingjing.doujuan.infrastructure.constants.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

/**
 * 验证码领域服务
 * 负责生成、存储和校验短信验证码
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationCodeService {

    private static final String SMS_CODE_KEY_PREFIX = "sms:code:";
    private static final String SMS_SEND_INTERVAL_KEY_PREFIX = "sms:interval:";

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${sms.code.length:6}")
    private int codeLength;

    @Value("${sms.code.expire-minutes:10}")
    private int expireMinutes;

    @Value("${sms.code.send-interval-seconds:60}")
    private int sendIntervalSeconds;

    private final SecureRandom secureRandom = new SecureRandom();

    /**
     * 生成并存储验证码
     *
     * @param phone 手机号
     * @return 生成的验证码
     */
    public String generateAndStoreCode(String phone) {
        // 检查发送频率
        String intervalKey = SMS_SEND_INTERVAL_KEY_PREFIX + phone;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(intervalKey))) {
            throw new IllegalStateException(ErrorMessage.CODE_SEND_TOO_FREQUENT);
        }

        // 生成随机验证码
        String code = generateRandomCode();

        // 存储验证码
        String codeKey = SMS_CODE_KEY_PREFIX + phone;
        redisTemplate.opsForValue().set(codeKey, code, expireMinutes, TimeUnit.MINUTES);

        // 设置发送频率限制
        redisTemplate.opsForValue().set(intervalKey, "1", sendIntervalSeconds, TimeUnit.SECONDS);

        log.info("Verification code generated for phone: {}, expire in {} minutes", phone, expireMinutes);
        return code;
    }

    /**
     * 校验验证码
     * 校验成功后会删除验证码，防止重复使用
     *
     * @param phone 手机号
     * @param code  验证码
     */
    public void verifyCode(String phone, String code) {
        String codeKey = SMS_CODE_KEY_PREFIX + phone;
        String storedCode = redisTemplate.opsForValue().get(codeKey);

        if (storedCode == null) {
            throw new IllegalArgumentException(ErrorMessage.CODE_EXPIRED);
        }

        if (!storedCode.equals(code)) {
            throw new IllegalArgumentException(ErrorMessage.CODE_INVALID);
        }

        // 校验成功，删除验证码
        redisTemplate.delete(codeKey);
        log.info("Verification code verified successfully for phone: {}", phone);
    }

    /**
     * 生成随机数字验证码
     *
     * @return 验证码字符串
     */
    private String generateRandomCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            code.append(secureRandom.nextInt(10));
        }
        return code.toString();
    }
}
