package com.zoro.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CapitalTradeOrderDto implements Serializable {

    private static final long serialVersionUID = 6627401903410124642L;
    
    private long selfUserId;

    private long oppositeUserId;

    private String orderTitle;

    private String merchantOrderNo;

    private BigDecimal amount;


}
