package com.reportingservice.reportingservice;

import com.reportingservice.reportingservice.Daos.*;
import com.reportingservice.reportingservice.Dtos.Order;
import com.reportingservice.reportingservice.Dtos.Portfolio;
import com.reportingservice.reportingservice.Dtos.StockTransactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MyDao {

    public static UserDao userDao;
    @Autowired
    private UserDao userDao1;

    public static PortfolioDao portfolioDao;
    @Autowired
    private PortfolioDao portfolioDao1;

    public static OrderDao orderDao;
    @Autowired
    private OrderDao orderDao1;


    public static ExchangeDao exchangeDao;
    @Autowired
    private ExchangeDao exchangeDao1;


    public static StockTransactionsDao stockTransactionsDao;
    @Autowired
    private StockTransactionsDao stockTransactionsDao1;

    @PostConstruct
    private void initStaticDao(){
        stockTransactionsDao = this.stockTransactionsDao1;
        exchangeDao = this.exchangeDao1;
        orderDao = this.orderDao1;
        portfolioDao = this.portfolioDao1;
        userDao = this.userDao1;

    }
}
