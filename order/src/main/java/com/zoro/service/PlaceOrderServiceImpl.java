package com.zoro.service;

import com.zoro.entity.Order;
import org.apache.commons.lang3.tuple.Pair;
import org.mengyun.tcctransaction.CancellingException;
import org.mengyun.tcctransaction.ConfirmingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Service
public class PlaceOrderServiceImpl {


    @Autowired
    OrderService orderService;

    @Autowired
    PaymentServiceImpl paymentService;

    public String placeOrder(long payerUserId, long payeeUserId, final BigDecimal redPacketPayAmount,final BigDecimal totalAmount) {

        Order order = new Order() ;
        order.setPayerUserId(payerUserId);
        order.setPayeeUserId(payeeUserId);
        order.setMerchantOrderNo(UUID.randomUUID().toString().replaceAll("-",""));
        order.setRedPacketPayAmount(redPacketPayAmount);
        order.setCapitalPayAmount(totalAmount.subtract(redPacketPayAmount));

        orderService.createOrder(order);

        Boolean result = false;

        try {

//            ExecutorService executorService = Executors.newFixedThreadPool(2);

//            Future future1 = executorService.submit(new Runnable() {
//                @Override
//                public void run() {
                    paymentService.makePayment(order.getMerchantOrderNo(), order, redPacketPayAmount, totalAmount.subtract(redPacketPayAmount));
//                }
//            });

//            Future future2 = executorService.submit(new Runnable() {
//                @Override
//                public void run() {
//                    paymentService.makePayment(order.getMerchantOrderNo(), order, redPacketPayAmount, order.getTotalAmount().subtract(redPacketPayAmount));
//                }
//            });
//
//            future1.get();
//            future2.get();

        } catch (ConfirmingException confirmingException) {
            //exception throws with the tcc transaction status is CONFIRMING,
            //when tcc transaction is confirming status,
            // the tcc transaction recovery will try to confirm the whole transaction to ensure eventually consistent.

            result = true;
        } catch (CancellingException cancellingException) {
            //exception throws with the tcc transaction status is CANCELLING,
            //when tcc transaction is under CANCELLING status,
            // the tcc transaction recovery will try to cancel the whole transaction to ensure eventually consistent.
        } catch (Throwable e) {
            //other exceptions throws at TRYING stage.
            //you can retry or cancel the operation.
            e.printStackTrace();
        }

        return order.getMerchantOrderNo();
    }
}
