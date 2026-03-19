package com.lingjing.doujuan.controller.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户信息修改请求DTO
 */
@Setter
@Getter
public class UserInfoModifyRequest {

    // Getters and Setters
    @Size(min = 1, max = 50, message = "昵称长度应为1-50个字符")
    private String nickName;
    
    @Pattern(regexp = "^https?://.*", message = "头像必须是有效的URL")
    private String avatar;
    
    @Size(max = 50, message = "真实姓名长度不能超过50个字符")
    private String realName;
    
    @Pattern(regexp = "^(男|女|其他)$", message = "性别必须是男、女或其他")
    private String gender;
    
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "出生日期格式必须为YYYY-MM-DD")
    private String birthDate;

}