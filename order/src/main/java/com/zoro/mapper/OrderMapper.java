package com.zoro.mapper;

import com.zoro.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    public int createOrder(Order order);

    public int updateOrder(Order order);

    Order findByMerchantOrderNo(String merchantOrderNo);
}
