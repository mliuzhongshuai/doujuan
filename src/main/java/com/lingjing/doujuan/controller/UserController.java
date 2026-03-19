package com.lingjing.doujuan.controller;
import com.lingjing.doujuan.application.UserApplication;
import com.lingjing.doujuan.controller.dto.UserInfoModifyRequest;
import com.lingjing.doujuan.infrastructure.utils.ApiResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 * 处理用户相关的HTTP请求
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserApplication userApplication;

    /**
     * 构造函数
     *
     * @param userApplication 用户应用服务
     */
    public UserController(UserApplication userApplication) {
        this.userApplication = userApplication;
    }

    /**
     * 修改用户信息
     *
     * @param request 用户信息修改请求DTO
     * @return 操作结果
     */
    @PostMapping("/info/modify")
    public ApiResult<String> modify(@Valid @RequestBody UserInfoModifyRequest request) {
        // 调用用户应用服务修改用户信息
        userApplication.modifyUserInfo(request);
        return ApiResult.success("修改成功");
    }
}
