package com.zoro.service;

import com.zoro.dto.RedPacketTradeOrderDto;
import com.zoro.entity.RedPacketAccount;
import com.zoro.entity.TradeOrder;
import com.zoro.mapper.RedPacketAccountMapper;
import com.zoro.mapper.TradeOrderMapper;
import com.zoro.redpacket.api.RedPacketTradeOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.dubbo.context.DubboTransactionContextEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

@Service("redPacketTradeOrderService")
@Slf4j
public class RedPacketTradeOrderServiceImpl implements RedPacketTradeOrderService {

    @Autowired
    RedPacketAccountMapper redPacketAccountMapper;

    @Autowired
    TradeOrderMapper tradeOrderMapper;

    @Override
    @Compensable(confirmMethod = "confirmRecord", cancelMethod = "cancelRecord", transactionContextEditor = DubboTransactionContextEditor.class)
    @Transactional
    public String record(RedPacketTradeOrderDto tradeOrderDto) {

        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("red packet try record called. time seq:{},订单id{}",DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"),tradeOrderDto.getMerchantOrderNo());


        TradeOrder foundTradeOrder = tradeOrderMapper.findByMerchantOrderNo(tradeOrderDto.getMerchantOrderNo());

        //check if trade order has been recorded, if yes, return success directly.
        if (foundTradeOrder == null) {

            TradeOrder tradeOrder = new TradeOrder(
                    tradeOrderDto.getSelfUserId(),
                    tradeOrderDto.getOppositeUserId(),
                    tradeOrderDto.getMerchantOrderNo(),
                    tradeOrderDto.getAmount()
            );

            try {

                tradeOrderMapper.insert(tradeOrder);

                RedPacketAccount transferFromAccount = redPacketAccountMapper.findByUserId(tradeOrderDto.getSelfUserId());

                transferFromAccount.transferFrom(tradeOrderDto.getAmount());

                int result = redPacketAccountMapper.update(transferFromAccount);
                if (result == 0) {
                    throw new RuntimeException("红包余额余额不足");
                }
            } catch (DataIntegrityViolationException e) {
                //this exception may happen when insert trade order concurrently, if happened, ignore this insert operation.
            }catch (RuntimeException e){
                throw new RuntimeException(e);
            }
        }

        return "success";
    }

    @Transactional
    public void confirmRecord(RedPacketTradeOrderDto tradeOrderDto) {

        log.info("red packet confirm record called. time seq:{},订单id{}",DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"),tradeOrderDto.getMerchantOrderNo());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            log.error("{}",e);
            throw new RuntimeException(e.getMessage());
        }
        TradeOrder tradeOrder = tradeOrderMapper.findByMerchantOrderNo(tradeOrderDto.getMerchantOrderNo());

        //check if the trade order status is DRAFT, if yes, return directly, ensure idempotency.
        if (tradeOrder != null && tradeOrder.getStatus().equals("DRAFT")) {
            tradeOrder.confirm();
            tradeOrder.updateVersion();
            tradeOrderMapper.update(tradeOrder);

            RedPacketAccount transferToAccount = redPacketAccountMapper.findByUserId(tradeOrderDto.getOppositeUserId());

            transferToAccount.transferTo(tradeOrderDto.getAmount());

            redPacketAccountMapper.update(transferToAccount);
        }
    }

    @Transactional
    public void cancelRecord(RedPacketTradeOrderDto tradeOrderDto) {

        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("red packet cancel record called. time seq:{},订单id{}",DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"),tradeOrderDto.getMerchantOrderNo());

        TradeOrder tradeOrder = tradeOrderMapper.findByMerchantOrderNo(tradeOrderDto.getMerchantOrderNo());

        //check if the trade order status is DRAFT, if yes, return directly, ensure idempotency.
        if (null != tradeOrder && "DRAFT".equals(tradeOrder.getStatus())) {
            tradeOrder.cancel();
            tradeOrder.updateVersion();
            tradeOrderMapper.update(tradeOrder);

            RedPacketAccount capitalAccount = redPacketAccountMapper.findByUserId(tradeOrderDto.getSelfUserId());

            capitalAccount.cancelTransfer(tradeOrderDto.getAmount());

            redPacketAccountMapper.update(capitalAccount);
        }
    }
}
