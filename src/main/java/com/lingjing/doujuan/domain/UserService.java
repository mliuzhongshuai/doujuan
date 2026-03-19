package com.lingjing.doujuan.domain;

import com.lingjing.doujuan.infrastructure.constants.ErrorMessage;
import com.lingjing.doujuan.infrastructure.storage.UserRepository;
import com.lingjing.doujuan.infrastructure.storage.po.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 修改用户信息
     * 1. 根据用户ID获取用户信息
     * 2. 更新用户信息
     * 3. 保存更新后的用户信息
     *
     * @param userId    用户ID
     * @param nickName  昵称
     * @param avatar    头像
     * @param realName  真实姓名
     * @param gender    性别
     * @param birthDate 出生日期
     */
    public void modifyUserInfo(int userId, String nickName, String avatar, String realName, String gender, String birthDate) {
        User user = userRepository.selectById(userId);

        if (user == null) {
            throw new IllegalArgumentException(ErrorMessage.USER_NOT_FOUND);
        }

        if (nickName != null) {
            user.setNickName(nickName);
        }
        if (avatar != null) {
            user.setAvatar(avatar);
        }
        if (realName != null) {
            user.setRealName(realName);
        }
        if (gender != null) {
            user.setGender(gender);
        }
        if (birthDate != null) {
            user.setBirthDate(LocalDate.parse(birthDate));
        }
        userRepository.updateById(user);
    }

}
