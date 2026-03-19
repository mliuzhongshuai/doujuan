package com.lingjing.doujuan.infrastructure.sms;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.lingjing.doujuan.infrastructure.constants.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 阿里云短信服务实现
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "aliyun.sms", name = "enabled", havingValue = "true")
public class AliyunSmsService implements SmsService {

    private final Client client;
    private final String signName;
    private final String templateCode;

    public AliyunSmsService(@Value("${aliyun.sms.access-key-id}") String accessKeyId,
                             @Value("${aliyun.sms.access-key-secret}") String accessKeySecret,
                             @Value("${aliyun.sms.sign-name}") String signName,
                             @Value("${aliyun.sms.template-code}") String templateCode) {
        this.signName = signName;
        this.templateCode = templateCode;
        try {
            Config config = new Config()
                    .setAccessKeyId(accessKeyId)
                    .setAccessKeySecret(accessKeySecret);
            config.endpoint = "dysmsapi.aliyuncs.com";
            this.client = new Client(config);
        } catch (Exception e) {
            log.error("Failed to initialize Aliyun SMS client", e);
            throw new RuntimeException("Failed to initialize Aliyun SMS client", e);
        }
    }

    @Override
    public void sendVerificationCode(String phone, String code) {
        try {
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(phone)
                    .setSignName(signName)
                    .setTemplateCode(templateCode)
                    .setTemplateParam("{\"code\":\"" + code + "\"}");

            RuntimeOptions runtime = new RuntimeOptions();
            client.sendSmsWithOptions(sendSmsRequest, runtime);
            log.info("SMS verification code sent successfully to phone: {}", phone);
        } catch (Exception e) {
            log.error("Failed to send SMS verification code to phone: {}", phone, e);
            throw new RuntimeException(ErrorMessage.SMS_SEND_FAILED, e);
        }
    }
}
