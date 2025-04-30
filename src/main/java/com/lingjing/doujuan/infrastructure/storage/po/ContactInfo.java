package com.lingjing.doujuan.infrastructure.storage.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("contact_info")
public class ContactInfo {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String contactType;

    private String contactValue;

    private Boolean isPrimary;

    private Boolean isDisabled;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
