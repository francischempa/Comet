package com.comet.orderserviceclient.Services;

import com.comet.orderserviceclient.Daos.StockTransactionsDao;
import com.comet.orderserviceclient.Dtos.StockTransactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockTransactionsService {

    @Autowired
    StockTransactionsDao stockTransactionsDao;

    public List<StockTransactions> allTransactions(){
        return stockTransactionsDao.findAll();
    }

    public List<StockTransactions> getOrders(){
        return stockTransactionsDao.getOpenOrders();
    }
}
