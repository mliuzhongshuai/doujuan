package com.lingjing.doujuan.infrastructure.storage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingjing.doujuan.infrastructure.storage.po.AuthenticationPassword;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthenticationPasswordRepository extends BaseMapper<AuthenticationPassword> {
}
