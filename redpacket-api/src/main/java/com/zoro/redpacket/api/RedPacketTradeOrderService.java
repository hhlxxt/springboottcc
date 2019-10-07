package com.zoro.redpacket.api;

import com.zoro.dto.RedPacketTradeOrderDto;
import org.mengyun.tcctransaction.api.Compensable;


public interface RedPacketTradeOrderService {

    @Compensable
    public String record(RedPacketTradeOrderDto tradeOrderDto);
}
