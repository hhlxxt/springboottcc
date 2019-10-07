package com.zoro.redpacket.api;

import java.math.BigDecimal;


public interface RedPacketAccountService {
    BigDecimal getRedPacketAccountByUserId(long userId);
}
