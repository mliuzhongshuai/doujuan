package com.lingjing.doujuan.application;

import cn.dev33.satoken.exception.NotPermissionException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationApplication {


    public void loginPassword(){
        throw new NotPermissionException("未授权异常");
    }

}
