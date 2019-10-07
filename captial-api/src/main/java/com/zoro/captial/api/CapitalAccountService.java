package com.zoro.captial.api;


import java.io.Serializable;
import java.math.BigDecimal;

public interface CapitalAccountService extends Serializable {

    BigDecimal getCapitalAccountByUserId(long userId);
}
