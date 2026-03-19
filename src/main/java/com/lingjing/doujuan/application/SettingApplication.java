package com.lingjing.doujuan.application;

import com.lingjing.doujuan.controller.dto.UserSettingResponse;
import com.lingjing.doujuan.infrastructure.storage.UserRepository;
import com.lingjing.doujuan.infrastructure.storage.po.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 设置应用服务
 * 协调用户设置相关的用例，调用domain层服务
 */
@Service
@RequiredArgsConstructor
public class SettingApplication {

    private final UserRepository userRepository;

    /**
     * 获取用户设置
     * 1. 验证用户ID
     * 2. 根据用户ID查询用户信息
     * 3. 将用户信息转换为响应DTO
     *
     * @param userId 用户ID
     * @return 用户设置响应
     */
    public UserSettingResponse getSetting(String userId) {
        // 验证用户ID
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        // 根据用户ID查询用户信息
        User user = userRepository.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getIdCardNumber, userId));
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + userId);
        }
        // 将用户信息转换为响应DTO
        return convertToDto(user);
    }

    /**
     * 将用户实体转换为设置响应DTO
     *
     * @param user 用户实体
     * @return 用户设置响应DTO
     */
    private UserSettingResponse convertToDto(User user) {
        UserSettingResponse dto = new UserSettingResponse();
        dto.setId(user.getId());
        dto.setNickName(user.getNickName());
        dto.setAvatar(user.getAvatar());
        dto.setRealName(user.getRealName());
        dto.setGender(user.getGender());
        dto.setBirthDate(user.getBirthDate() != null ? user.getBirthDate().toString() : null);
        dto.setIdCardNumber(user.getIdCardNumber());
        //dto.setEmail(user.getEmail());
        //dto.setAddress(user.getAddress());
        return dto;
    }
}
