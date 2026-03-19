package com.lingjing.doujuan.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 发送短信验证码请求DTO
 */
@Data
public class SendSmsCodeRequest {

    @NotBlank(message = "手机号不能为空")
    private String phone;
}
