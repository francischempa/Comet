package com.comet.orderserviceclient.Services;

import com.comet.orderserviceclient.Daos.ExchangeDao;
import com.comet.orderserviceclient.Dtos.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeService {

    @Autowired
    ExchangeDao exchangeDao;
    //all active exchanges
    public List<Exchange> allActiveExchange(){
        return exchangeDao.findAll();
    }
}
