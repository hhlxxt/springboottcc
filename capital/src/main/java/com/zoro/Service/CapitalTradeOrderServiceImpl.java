package com.zoro.Service;

import com.zoro.Mapper.CapAccountMapper;
import com.zoro.Mapper.CapTradeOrderMapper;
import com.zoro.captial.api.CapitalTradeOrderService;
import com.zoro.dto.CapitalTradeOrderDto;
import com.zoro.entity.CapAccount;
import com.zoro.entity.CapTradeOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.dubbo.context.DubboTransactionContextEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

@Slf4j
@Service("capitalTradeOrderService")
public class CapitalTradeOrderServiceImpl implements CapitalTradeOrderService {

    @Autowired
    CapAccountMapper capitalAccountMapper;

    @Autowired
    CapTradeOrderMapper captialTradeOrderMapper;

    @Override
    @Compensable(confirmMethod = "confirmRecord", cancelMethod = "cancelRecord", transactionContextEditor = DubboTransactionContextEditor.class)
    @Transactional
    public String record(CapitalTradeOrderDto tradeOrderDto) {

        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("capital try record called. time seq:{},订单id{}",DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"),tradeOrderDto.getMerchantOrderNo());


        CapTradeOrder foundTradeOrder = captialTradeOrderMapper.findByMerchantOrderNo(tradeOrderDto.getMerchantOrderNo());


        //check if trade order has been recorded, if yes, return success directly.
        if (foundTradeOrder == null) {

            CapTradeOrder tradeOrder = new CapTradeOrder(
                    tradeOrderDto.getSelfUserId(),
                    tradeOrderDto.getOppositeUserId(),
                    tradeOrderDto.getMerchantOrderNo(),
                    tradeOrderDto.getAmount()
            );

            try {
                captialTradeOrderMapper.insert(tradeOrder);

                CapAccount transferFromAccount = capitalAccountMapper.getCapitalAccountByUserId(tradeOrderDto.getSelfUserId());

                transferFromAccount.transferFrom(tradeOrderDto.getAmount());

                long result = capitalAccountMapper.updateBalance(transferFromAccount);
                if (result == 0) {
                    log.info("账户余额不足");
                    throw new RuntimeException("账户余额不足");
                }

            } catch (DataIntegrityViolationException e) {
                //this exception may happen when insert trade order concurrently, if happened, ignore this insert operation.
            }catch (RuntimeException e){
                throw new RuntimeException(e);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return "success";
    }

    @Transactional
    public void confirmRecord(CapitalTradeOrderDto tradeOrderDto) {
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("capital confirm record called. time seq:{},订单id{}",DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"),tradeOrderDto.getMerchantOrderNo());

        CapTradeOrder tradeOrder = captialTradeOrderMapper.findByMerchantOrderNo(tradeOrderDto.getMerchantOrderNo());

        //check if the trade order status is DRAFT, if yes, return directly, ensure idempotency.
        if (tradeOrder != null && tradeOrder.getStatus().equals("DRAFT")) {
            tradeOrder.setStatus("CONFIRM");
            tradeOrder.setVersion(tradeOrder.getVersion()+1);
            captialTradeOrderMapper.update(tradeOrder);

            CapAccount transferToAccount = capitalAccountMapper.getCapitalAccountByUserId(tradeOrderDto.getOppositeUserId());

            transferToAccount.transferTo(tradeOrderDto.getAmount());

            long result = capitalAccountMapper.updateBalance(transferToAccount);
            if (result == 0) {
                log.info("根据版本号更新账户余额失败");
                throw new RuntimeException("账户余额不足");
            }
        }
    }

    @Transactional
    public void cancelRecord(CapitalTradeOrderDto tradeOrderDto) {
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("capital cancel record called. time seq:{},订单id{}",DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"),tradeOrderDto.getMerchantOrderNo());

        CapTradeOrder tradeOrder = captialTradeOrderMapper.findByMerchantOrderNo(tradeOrderDto.getMerchantOrderNo());

        //check if the trade order status is DRAFT, if yes, return directly, ensure idempotency.
        if (null != tradeOrder && "DRAFT".equals(tradeOrder.getStatus())) {
            tradeOrder.setStatus("CANCEL");
            tradeOrder.setVersion(tradeOrder.getVersion()+1);
            captialTradeOrderMapper.update(tradeOrder);

            CapAccount capitalAccount = capitalAccountMapper.getCapitalAccountByUserId(tradeOrderDto.getSelfUserId());

            capitalAccount.cancelTransfer(tradeOrderDto.getAmount());

            capitalAccountMapper.updateBalance(capitalAccount);
        }
    }
}
