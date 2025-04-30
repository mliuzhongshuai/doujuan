package com.lingjing.doujuan.controller;


import com.lingjing.doujuan.application.AuthenticationApplication;
import com.lingjing.doujuan.infrastructure.utils.ApiResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationApplication authenticationApplication;

    public AuthenticationController(AuthenticationApplication authenticationApplication) {
        this.authenticationApplication = authenticationApplication;
    }



    @PostMapping("/phone/register")
    public ApiResult<String> loginRegister() {
        authenticationApplication.loginPassword();
        return ApiResult.success("注册成功");
    }


    @PostMapping("/password/login")
    public ApiResult<String> loginPassword() {
        authenticationApplication.loginPassword();
        return ApiResult.success("登陆成功");
    }


    @PostMapping("/phone/login")
    public ApiResult<String> loginPhone() {
        authenticationApplication.loginPassword();
        return ApiResult.success("登陆成功");
    }







}
