package com.lingjing.doujuan.controller;


import com.lingjing.doujuan.application.AuthenticationApplication;
import com.lingjing.doujuan.controller.dto.PhoneCodeRequest;
import com.lingjing.doujuan.controller.dto.PhonePasswordRequest;
import com.lingjing.doujuan.controller.dto.PhoneRegisterRequest;
import com.lingjing.doujuan.controller.dto.SendSmsCodeRequest;
import com.lingjing.doujuan.infrastructure.utils.ApiResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationApplication authenticationApplication;

    public AuthenticationController(AuthenticationApplication authenticationApplication) {
        this.authenticationApplication = authenticationApplication;
    }

    @PostMapping("/phone/code/send")
    public ApiResult<String> sendSmsCode(@Valid @RequestBody SendSmsCodeRequest request) {
        authenticationApplication.sendSmsCode(request.getPhone());
        return ApiResult.success("验证码发送成功");
    }

    @PostMapping("/phone/register")
    public ApiResult<String> register(@Valid @RequestBody PhoneRegisterRequest request) {
        String token = authenticationApplication.register(request.getPhone(), request.getCode(), request.getPassword());
        return ApiResult.success(token, "注册成功");
    }

    @PostMapping("/phone/code/login")
    public ApiResult<String> loginCodePhone(@Valid @RequestBody PhoneCodeRequest request) {
        String token = authenticationApplication.loginCode(request.getPhone(), request.getCode());
        return ApiResult.success(token, "登录成功");
    }

    @PostMapping("/phone/password/login")
    public ApiResult<String> loginPasswordPhone(@Valid @RequestBody PhonePasswordRequest request) {
        String token = authenticationApplication.loginPassword(request.getPhone(), request.getPassword());
        return ApiResult.success(token, "登录成功");
    }
}

