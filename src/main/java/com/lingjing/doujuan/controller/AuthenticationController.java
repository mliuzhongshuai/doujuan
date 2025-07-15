package com.lingjing.doujuan.controller;


import com.lingjing.doujuan.application.AuthenticationApplication;
import com.lingjing.doujuan.infrastructure.utils.ApiResult;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationApplication authenticationApplication;

    public AuthenticationController(AuthenticationApplication authenticationApplication) {
        this.authenticationApplication = authenticationApplication;
    }

    /**
     * 处理用户通过手机号进行注册的 POST 请求。
     * 目前该方法调用了 authenticationApplication 的 loginPassword 方法，
     * 但传入的手机号、验证码和密码参数未被使用，可能需要后续完善逻辑。
     *
     * @param phone    用户注册时使用的手机号
     * @param code     用户注册时的验证码
     * @param password 用户注册时设置的密码
     * @return 一个 ApiResult 对象，包含注册成功的消息
     */
    @PostMapping("/phone/register")
    public ApiResult<String> register(@RequestParam String phone, @PathVariable String code, String password) {
        //这里对参数进行必要验证

        // 调用认证应用服务的登录密码方法
        authenticationApplication.loginPassword();
        return ApiResult.success("注册成功");
    }

    /**
     * 处理用户通过手机号和验证码进行登录的 POST 请求。
     * 目前该方法调用了 authenticationApplication 的 loginPassword 方法，
     * 但传入的手机号和验证码参数未被使用，可能需要后续完善逻辑。
     *
     * @param phone 用户登录时使用的手机号
     * @param code  用户登录时的验证码
     * @return 一个 ApiResult 对象，包含登录成功的消息
     */
    @PostMapping("/phone/code/login")
    public ApiResult<String> loginCodePhone(String phone, String code) {
        // 调用认证应用服务的登录密码方法
        authenticationApplication.loginPassword();
        // 返回一个包含登录成功消息的 ApiResult 对象
        return ApiResult.success("登陆成功");
    }



    /**
     * 处理用户通过手机号和密码进行登录的 POST 请求。
     * 目前该方法调用了 authenticationApplication 的 loginPassword 方法，
     * 但传入的验证码参数未被使用，可能需要后续完善逻辑以实现正确的密码登录流程。
     *
     * @param phone 用户登录时使用的手机号
     * @param code  用户登录时传入的验证码，当前未被使用
     * @return 一个 ApiResult 对象，包含登录成功的消息
     */
    @PostMapping("/phone/password/login")
    public ApiResult<String> loginPasswordPhone(String phone, String code) {
        // 调用认证应用服务的登录密码方法
        authenticationApplication.loginPassword();
        // 返回一个包含登录成功消息的 ApiResult 对象
        return ApiResult.success("登陆成功");
    }







}
