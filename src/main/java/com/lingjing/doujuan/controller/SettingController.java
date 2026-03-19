package com.lingjing.doujuan.controller;

import com.lingjing.doujuan.application.SettingApplication;
import com.lingjing.doujuan.controller.dto.UserSettingResponse;
import com.lingjing.doujuan.infrastructure.utils.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设置控制器
 * 处理用户设置相关的HTTP请求
 */
@RestController
@RequestMapping("/setting")
public class SettingController {

    private final SettingApplication settingApplication;

    /**
     * 构造函数
     *
     * @param settingApplication 设置应用服务
     */
    public SettingController(SettingApplication settingApplication) {
        this.settingApplication = settingApplication;
    }

    /**
     * 获取用户设置
     *
     * @param userId 用户ID
     * @return 用户设置响应
     */
    @GetMapping
    public ApiResult<UserSettingResponse> getSetting(@RequestParam String userId) {
        // 调用设置应用服务获取用户设置
        UserSettingResponse user = settingApplication.getSetting(userId);
        return ApiResult.success(user);
    }
}


