package com.zoro;

import com.zoro.redpacket.api.RedPacketAccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class RedpacketTest {
    /*@Autowired
    private RedPacketAccountService redPacketAccountService;

    @Test
    public void getRedPacketAccountByUserId(){
        BigDecimal balance = redPacketAccountService.getRedPacketAccountByUserId(1000);
        System.err.println(balance);

    }*/

    @Test
    public void zkNode() {
        ZooKeeper zooKeeper = null ;
        try {
            CountDownLatch latch = new CountDownLatch(1);
            Watcher watcher = new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {

                    log.info("连接zk的状态:{}",watchedEvent.getState());
                    if (watchedEvent.getState().equals(Event.KeeperState.SyncConnected)){
                        latch.countDown();
                    }
                }
            };
            zooKeeper = new ZooKeeper("127.0.0.1:2181",5000,watcher);

            latch.await();
            log.info("连接zk成功");
            String path = "/dubbo/com.zoro.captial.api.CapitalTradeOrderService/providers" ;
            List<String> children = zooKeeper.getChildren(path, true);
            log.info("{}节点下的子节点{}",path,children.get(0));
           /* byte[] data = zooKeeper.getData(path+"/"+children.get(0), true, null);
            log.info("{}节点下的数据为{}",path,new String(data));*/

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (zooKeeper == null) {
                try {
                    zooKeeper.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void bigDecimalTest(){
        log.info("test  BigDecimal negate:\t{}",new BigDecimal(1000000000).negate());
    }
}
