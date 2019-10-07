package com.zoro.mapper;

import com.zoro.entity.RedPacketAccount;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RedPacketAccountMapper {
    RedPacketAccount findByUserId(long userId);

    int update(RedPacketAccount redPacketAccount);
}
