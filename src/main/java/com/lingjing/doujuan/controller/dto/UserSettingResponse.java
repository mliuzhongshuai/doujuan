package com.lingjing.doujuan.controller.dto;

import lombok.Data;

@Data
public class UserSettingResponse {
    private Integer id;
    private String nickName;
    private String avatar;
    private String realName;
    private String gender;
    private String birthDate;
    private String idCardNumber;
    private String email;
    private String address;
}