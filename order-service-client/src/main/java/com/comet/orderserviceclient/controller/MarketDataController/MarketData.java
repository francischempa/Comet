package com.comet.orderserviceclient.controller.MarketDataController;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

@RestController
public class MarketData {

    @CrossOrigin
    @GetMapping(path = "/marketdata")
    public String marketData(){
        String prefix = "md";
        Jedis jedis = new Jedis("192.168.128.253");
        String currentMarketData = jedis.get(prefix);

        return currentMarketData;
    }
}
