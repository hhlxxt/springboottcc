package com.zoro.service;

import com.zoro.mapper.RedPacketAccountMapper;
import com.zoro.redpacket.api.RedPacketAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service("redPacketAccountService")
public class RedPacketAccountServiceImpl implements RedPacketAccountService {

    @Autowired
    RedPacketAccountMapper redPacketAccountMapper;

    @Override
    public BigDecimal getRedPacketAccountByUserId(long userId) {
        return redPacketAccountMapper.findByUserId(userId).getBalanceAmount();
    }
}
