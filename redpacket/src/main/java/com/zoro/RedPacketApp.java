package com.zoro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.zoro.Mapper.*"})
public class RedPacketApp {
    public static void main(String[] args) {
        SpringApplication.run(RedPacketApp.class,args);
    }
}
