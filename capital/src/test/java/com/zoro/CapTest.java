package com.zoro;

import com.zoro.Mapper.CapTradeOrderMapper;
import com.zoro.captial.api.CapitalAccountService;
import com.zoro.captial.api.CapitalTradeOrderService;
import com.zoro.entity.CapAccount;
import com.zoro.entity.CapTradeOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CapTest {
    @Autowired
    private CapitalAccountService capitalAccountService ;

    @Autowired
    CapTradeOrderMapper captialTradeOrderMapper;

    @Test
    public void testGetCapitalAccountByUserId(){
        BigDecimal capAccount = capitalAccountService.getCapitalAccountByUserId(1000);
        System.err.println(capAccount);

    }

    @Test
    public void findByMerchantOrderNo(){
        CapTradeOrder capTradeOrder = captialTradeOrderMapper.findByMerchantOrderNo("6365ede1f7e5471597091eb5cb9defb5");
        System.err.println(capTradeOrder);

    }

}
