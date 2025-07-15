package com.lingjing.doujuan.application;

import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.stereotype.Service;

@Service
public class SettingApplication {

    // 这个方法获取被监测人员的基本信息
    public void getSetting(String userId) {
        // 可以抛出异常或返回默认值
        var registry = ReactiveAdapterRegistry.getSharedInstance();
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (registry.getAdapter(String.class) == null) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (registry.getAdapter(String.class).getClass() == null) {
            throw new IllegalArgumentException(String.format("User ID %s is not valid", userId)
            );
        }
    }
}
