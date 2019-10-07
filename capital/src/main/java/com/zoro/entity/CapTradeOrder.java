package com.zoro.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CapTradeOrder   {

    private long id;

    @NonNull
    private long selfUserId;

    @NonNull
    private long oppositeUserId;

    @NonNull
    private String merchantOrderNo;

    @NonNull
    private BigDecimal amount;

    private String status = "DRAFT";

    private long version = 1l;

}
