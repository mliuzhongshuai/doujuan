package com.lingjing.doujuan.infrastructure.storage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingjing.doujuan.infrastructure.storage.po.Accounts;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountsRepository extends BaseMapper<Accounts> {
}
