package com.lingjing.doujuan.application;

import cn.dev33.satoken.stp.StpUtil;
import com.lingjing.doujuan.controller.dto.UserInfoModifyRequest;
import com.lingjing.doujuan.domain.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 用户应用服务
 * 协调用户相关的用例，调用domain层服务
 */
@Service
@RequiredArgsConstructor
public class UserApplication {

    private final UserService userService;

    /**
     * 修改用户信息
     * 1. 获取当前登录用户ID
     * 2. 调用用户服务修改用户信息
     *
     * @param request 用户信息修改请求DTO
     */
    public void modifyUserInfo(UserInfoModifyRequest request) {
        // 获取当前登录用户ID
        int userId = StpUtil.getLoginIdAsInt();
        // 调用用户服务修改用户信息
        userService.modifyUserInfo(userId, request.getNickName(), request.getAvatar(), request.getRealName(), request.getGender(), request.getBirthDate());
    }
}