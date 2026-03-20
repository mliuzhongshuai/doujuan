package com.lingjing.doujuan.application;

import com.lingjing.doujuan.controller.dto.UserSettingResponse;
import com.lingjing.doujuan.infrastructure.enums.Gender;
import com.lingjing.doujuan.infrastructure.storage.AccountsRepository;
import com.lingjing.doujuan.infrastructure.storage.UserRepository;
import com.lingjing.doujuan.infrastructure.storage.po.Accounts;
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
    private final AccountsRepository accountsRepository;

    /**
     * 获取用户设置
     * 1. 验证用户ID
     * 2. 根据用户ID查询用户信息和账户信息
     * 3. 将信息转换为响应DTO
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
        // 查询账户信息
        Accounts accounts = accountsRepository.selectOne(
                new LambdaQueryWrapper<Accounts>().eq(Accounts::getUserId, user.getId()));
        // 将信息转换为响应DTO
        return convertToDto(user, accounts);
    }

    /**
     * 将用户实体和账户实体转换为设置响应DTO
     *
     * @param user     用户实体
     * @param accounts 账户实体
     * @return 用户设置响应DTO
     */
    private UserSettingResponse convertToDto(User user, Accounts accounts) {
        UserSettingResponse dto = new UserSettingResponse();
        dto.setId(user.getId());
        dto.setRealName(user.getRealName());
        dto.setGender(user.getGender());
        dto.setBirthDate(user.getBirthDate() != null ? user.getBirthDate().toString() : null);
        dto.setIdCardNumber(user.getIdCardNumber());
        // 从账户信息获取昵称和头像
       /* if (accounts != null) {
            dto.setNickName(accounts.getNickName());
            dto.setAvatar(accounts.getAvatar());
        }*/
        //dto.setEmail(user.getEmail());
        //dto.setAddress(user.getAddress());
        return dto;
    }
}
