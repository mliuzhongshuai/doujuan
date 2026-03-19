package com.lingjing.doujuan.controller.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserModifyRequest {
    @Size(min = 1, max = 50, message = "昵称长度应为1-50个字符")
    private String nickName;

    @Pattern(regexp = "^https?://.*", message = "头像必须是有效的URL")
    private String avatar;

    @Size(min = 1, max = 100, message = "真实姓名长度应为1-100个字符")
    private String realName;

    @Pattern(regexp = "^(男|女|其他)$", message = "性别必须是男、女或其他")
    private String gender;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "生日格式应为 yyyy-MM-dd")
    private String birthDate;
}
