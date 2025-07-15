package com.lingjing.doujuan.infrastructure.config;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SaAuthorLoadConfig implements StpInterface {

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return Arrays.asList("101", "order.add", "user.*");
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return List.of("user");
    }

    //安全检测，用于标识连接是否安全
    public boolean isSafe(){
        return true;
    }
}
