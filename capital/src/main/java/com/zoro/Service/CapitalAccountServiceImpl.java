package com.zoro.Service;

import com.zoro.Mapper.CapAccountMapper;
import com.zoro.captial.api.CapitalAccountService;
import com.zoro.entity.CapAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service("capitalAccountService")
public class CapitalAccountServiceImpl implements CapitalAccountService {


    @Autowired
    CapAccountMapper capAccountMapper;

    @Override
    public BigDecimal getCapitalAccountByUserId(long userId) {
        return capAccountMapper.getCapitalAccountByUserId(userId).getBalanceAmount();
    }
}
