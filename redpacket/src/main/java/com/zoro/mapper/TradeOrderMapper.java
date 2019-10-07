package com.zoro.mapper;

import com.zoro.entity.TradeOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper

public interface TradeOrderMapper {
    void insert(TradeOrder tradeOrder);

    int update(TradeOrder tradeOrder);

    TradeOrder findByMerchantOrderNo(String merchantOrderNo);
}
