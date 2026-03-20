-- 豆卷应用数据库表结构
-- 基于实体类生成的DDL

-- 用户表
CREATE TABLE IF NOT EXISTS `users` (
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    `id_card_number` VARCHAR(18) DEFAULT NULL COMMENT '身份证号',
    `real_name` VARCHAR(50) DEFAULT NULL COMMENT '真实姓名',
    `gender` VARCHAR(10) DEFAULT NULL COMMENT '性别（男/女/其他）',
    `birth_date` DATE DEFAULT NULL COMMENT '出生日期',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_id_card_number` (`id_card_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户基本信息表';

-- 账户表
CREATE TABLE IF NOT EXISTS `accounts` (
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '账户ID',
    `user_id` INT NOT NULL COMMENT '关联的用户ID',
    `account_status` VARCHAR(20) DEFAULT 'ACTIVE' COMMENT '账户状态（ACTIVE/INACTIVE/BLOCKED）',
    `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录时间',
    `nick_name` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像URL',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_user_id` (`user_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户账户表';

-- 手机号认证表
CREATE TABLE IF NOT EXISTS `authentication_phone` (
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '认证ID',
    `user_id` INT NOT NULL COMMENT '关联的用户ID',
    `account_id` INT NOT NULL COMMENT '关联的账户ID',
    `phone_number` VARCHAR(20) NOT NULL COMMENT '手机号',
    `failed_attempts` INT DEFAULT 0 COMMENT '失败尝试次数',
    `last_attempt_time` DATETIME DEFAULT NULL COMMENT '最后尝试时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY `uk_phone_number` (`phone_number`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_account_id` (`account_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`account_id`) REFERENCES `accounts`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='手机号认证表';

-- 密码认证表
CREATE TABLE IF NOT EXISTS `authentication_password` ( 
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '认证ID',
    `user_id` INT NOT NULL COMMENT '关联的用户ID',
    `account_id` INT NOT NULL COMMENT '关联的账户ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password_hash` VARCHAR(255) NOT NULL COMMENT '密码哈希值',
    `failed_attempts` INT DEFAULT 0 COMMENT '失败尝试次数',
    `last_attempt_time` DATETIME DEFAULT NULL COMMENT '最后尝试时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY `uk_username` (`username`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_account_id` (`account_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`account_id`) REFERENCES `accounts`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='密码认证表';

-- 联系方式表
CREATE TABLE IF NOT EXISTS `contact_info` (
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '联系信息ID',
    `user_id` INT NOT NULL COMMENT '关联的用户ID',
    `contact_type` VARCHAR(20) NOT NULL COMMENT '联系方式类型（EMAIL/PHONE/WECHAT等）',
    `contact_value` VARCHAR(100) NOT NULL COMMENT '联系方式值',
    `is_primary` TINYINT(1) DEFAULT 0 COMMENT '是否为主要联系方式',
    `is_disabled` TINYINT(1) DEFAULT 0 COMMENT '是否已禁用',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_contact_type` (`contact_type`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户联系方式表';



