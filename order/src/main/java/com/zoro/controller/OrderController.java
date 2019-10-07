package com.zoro.controller;

import com.zoro.entity.Order;
import com.zoro.service.OrderService;
import com.zoro.service.PlaceOrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

@Controller
@Slf4j
public class OrderController {

    @Autowired
    PlaceOrderServiceImpl placeOrderService;

    @Autowired
    OrderService orderService ;

    @RequestMapping(value = "/placeorder", method = RequestMethod.GET)
    @ResponseBody
    public void placeOrder(@RequestParam BigDecimal redPacketPayAmount,
                           @RequestParam BigDecimal totalAmount,
                                   @RequestParam long payerUserId,
                                   @RequestParam long payeeUserId) {



        String merchantOrderNo = placeOrderService.placeOrder(payerUserId,payeeUserId ,redPacketPayAmount,
                totalAmount);

        Order foundOrder = orderService.findByMerchantOrderNo(merchantOrderNo);

        String payResultTip = null;

        if ("CONFIRMED".equals(foundOrder.getStatus()))
            payResultTip = "Success";
        else if ("PAY_FAILED".equals(foundOrder.getStatus()))
            payResultTip = "Failed, please see log";
        else
            payResultTip = "Unknown";
        log.info("支付结果{}",payResultTip);

    }


}
