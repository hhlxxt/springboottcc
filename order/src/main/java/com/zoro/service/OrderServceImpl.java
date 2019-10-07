package com.zoro.service;

import com.zoro.entity.Order;
import com.zoro.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper ;

    @Override
    @Transactional
    public void createOrder(Order order) {
        orderMapper.createOrder(order);

    }

    @Override
    public Order findByMerchantOrderNo(String merchantOrderNo) {
        return orderMapper.findByMerchantOrderNo(merchantOrderNo);
    }
}
