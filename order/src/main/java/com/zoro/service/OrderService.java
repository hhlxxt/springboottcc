package com.zoro.service;

import com.zoro.entity.Order;


public interface OrderService {

    public void createOrder(Order order);

    Order findByMerchantOrderNo(String merchantOrderNo);


}
