package com.zoro.Mapper;

import com.zoro.entity.CapAccount;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CapAccountMapper {
    CapAccount getCapitalAccountByUserId(long userId);

    long insert(CapAccount account);

    long updateBalance(CapAccount account);
}
