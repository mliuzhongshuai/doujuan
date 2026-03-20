package com.lingjing.doujuan.domain;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lingjing.doujuan.infrastructure.constants.ErrorMessage;
import com.lingjing.doujuan.infrastructure.enums.AccountStatus;
import com.lingjing.doujuan.infrastructure.storage.AccountsRepository;
import com.lingjing.doujuan.infrastructure.storage.AuthenticationPasswordRepository;
import com.lingjing.doujuan.infrastructure.storage.AuthenticationPhoneRepository;
import com.lingjing.doujuan.infrastructure.storage.UserRepository;
import com.lingjing.doujuan.infrastructure.storage.po.Accounts;
import com.lingjing.doujuan.infrastructure.storage.po.AuthenticationPassword;
import com.lingjing.doujuan.infrastructure.storage.po.AuthenticationPhone;
import com.lingjing.doujuan.infrastructure.storage.po.User;
import com.lingjing.doujuan.infrastructure.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 认证领域服务
 * 处理认证相关的核心业务逻辑
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AccountsRepository accountsRepository;
    private final AuthenticationPhoneRepository authenticationPhoneRepository;
    private final AuthenticationPasswordRepository authenticationPasswordRepository;
    private final VerificationCodeService verificationCodeService;

    /**
     * 手机号注册
     * 1. 创建用户基本信息
     * 2. 创建账户记录
     * 3. 绑定手机号认证信息
     * 4. 可选设置密码
     *
     * @param phone    手机号
     * @param code     验证码
     * @param password 密码（可选）
     * @return 用户ID
     */
    public Integer register(String phone, String code, String password) {
        // 验证验证码
        verificationCodeService.verifyCode(phone, code);
        // 创建用户基本信息
        User user = new User();
        userRepository.insert(user);
        // 创建账户记录
        Accounts accounts = new Accounts();
        accounts.setUserId(user.getId());
        accounts.setAccountStatus(AccountStatus.ACTIVE.getValue());
        accounts.setLastLoginTime(LocalDateTime.now());
        //accounts.setNickName("User_" + phone.substring(phone.length() - 4));
        accountsRepository.insert(accounts);
        // 绑定手机号认证信息
        AuthenticationPhone authPhone = new AuthenticationPhone();
        authPhone.setUserId(user.getId());
        authPhone.setAccountId(accounts.getId());
        authPhone.setPhoneNumber(phone);
        authPhone.setFailedAttempts(0);
        authenticationPhoneRepository.insert(authPhone);
        // 可选设置密码
        if (password != null && !password.isEmpty()) {
            AuthenticationPassword authPassword = new AuthenticationPassword();
            authPassword.setUserId(user.getId());
            authPassword.setAccountId(accounts.getId());
            authPassword.setUsername(phone);
            authPassword.setPasswordHash(PasswordUtil.encode(password));
            authPassword.setFailedAttempts(0);
            authenticationPasswordRepository.insert(authPassword);
        }
        return user.getId();
    }

    /**
     * 手机验证码登录
     * 1. 验证手机号是否已注册
     * 2. 验证验证码
     *
     * @param phone 手机号
     * @param code  验证码
     * @return 用户ID
     */
    public Integer loginCode(String phone, String code) {
        // 验证手机号是否已注册
        AuthenticationPhone authPhone = authenticationPhoneRepository.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<AuthenticationPhone>()
                        .eq(AuthenticationPhone::getPhoneNumber, phone));

        if (authPhone == null) {
            throw new IllegalArgumentException(ErrorMessage.PHONE_NOT_REGISTERED);
        }
        // 验证验证码
        verificationCodeService.verifyCode(phone, code);

        return authPhone.getUserId();
    }

    /**
     * 手机密码登录
     * 1. 根据用户名查找密码认证信息
     * 2. 验证密码是否正确
     *
     * @param phone    手机号/用户名
     * @param password 密码
     * @return 用户ID
     */
    public Integer loginPassword(String phone, String password) {
        // 根据用户名查找密码认证信息
        AuthenticationPassword authPassword = authenticationPasswordRepository.selectOne(
                new LambdaQueryWrapper<AuthenticationPassword>()
                        .eq(AuthenticationPassword::getUsername, phone));

        if (authPassword == null) {
            throw new IllegalArgumentException(ErrorMessage.USER_NOT_FOUND);
        }
        // 验证密码是否正确
        if (!PasswordUtil.matches(password, authPassword.getPasswordHash())) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_PASSWORD);
        }

        return authPassword.getUserId();
    }
}