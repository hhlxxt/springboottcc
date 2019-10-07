package com.zoro.captial.api;

import com.zoro.dto.CapitalTradeOrderDto;
import org.mengyun.tcctransaction.api.Compensable;

import java.io.Serializable;


public interface CapitalTradeOrderService extends Serializable {

    @Compensable
    public String record(CapitalTradeOrderDto tradeOrderDto);

}
