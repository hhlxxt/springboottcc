package com.zoro.Mapper;

import com.zoro.entity.CapTradeOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CapTradeOrderMapper {
    public void insert(CapTradeOrder capTradeOrder);
    public void update(CapTradeOrder capTradeOrder);
    public CapTradeOrder findByMerchantOrderNo(String merchantOrderNo);
}
