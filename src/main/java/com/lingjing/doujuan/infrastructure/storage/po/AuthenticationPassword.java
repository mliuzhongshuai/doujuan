package com.lingjing.doujuan.infrastructure.storage.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("authentication_password")
public class AuthenticationPassword {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer accountId;

    private String username;

    private String passwordHash;

    private Integer failedAttempts;

    private LocalDateTime lastAttemptTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
