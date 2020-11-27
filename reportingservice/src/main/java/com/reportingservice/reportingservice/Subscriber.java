package com.reportingservice.reportingservice;


import com.corundumstudio.socketio.SocketIOServer;
import com.reportingservice.reportingservice.Daos.*;
import com.reportingservice.reportingservice.Dtos.Exchange;
import com.reportingservice.reportingservice.Dtos.Order;
import com.reportingservice.reportingservice.Dtos.StockTransactions;
import com.reportingservice.reportingservice.Dtos.User;
import com.reportingservice.reportingservice.reportobject.ExchangeTransaction;
import com.reportingservice.reportingservice.reportobject.MonitorRequest;
import com.reportingservice.reportingservice.reportobject.OrderDetails;
import com.reportingservice.reportingservice.reportobject.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Subscriber {

//    UserDao userDao;
//    ExchangeDao exchangeDao;
//    OrderDao orderDao;
//    PortfolioDao portfolioDao;
//    StockTransactionsDao stockTransactionsDao;

    private final Jedis jedis;
    private final SocketIOServer server;
    private final List<String> channels = new ArrayList<>();

    public Subscriber(AnnotationConfigApplicationContext ctx){
//        userDao = ctx.getBean(UserDao.class);
//        exchangeDao = ctx.getBean(ExchangeDao.class);
//        orderDao = ctx.getBean(OrderDao.class);
//        portfolioDao = ctx.getBean(PortfolioDao.class);
//        stockTransactionsDao = ctx.getBean(StockTransactionsDao.class);

        this.server = ctx.getBean(SocketIOServer.class);
        this.jedis = ctx.getBean(Jedis.class);
        channels.add("tradeengine-transaction");

        channels.add("exch1-order-create");  // ORDER PLACED
        channels.add("exch1-order-update");  // ORDER EXECUTED
        channels.add("exch1-order-cancel");  // USER CANCELLED ORDER
        channels.add("exch1-order-failed");  // FAILED TO PLACE ORDER



        channels.add("ordervalidator-validation-success");
        channels.add("ordervalidator-validation-failed");
        channels.add("clientconnectivity");
        channels.add("marketdata");
    }

    public void subscribeToChannels(JedisPubSub jedisPubSub ){
        jedis.subscribe(jedisPubSub,channels.toArray(String[]::new));
    }

    public void addChannel(String channel){
        channels.add(channel);
    }

    public void register(){
        JedisPubSub payload = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                switch (channel){

                    case "exch1-order-create":{
                        handleExch1CreateOrder(message);
                    }break;
                    case "exch1-order-update":{
                        handleExch1UpdateOrder(message);
                    }break;
                    case "exch1-order-cancel":{
                        handleExch1CanceledOrders(message);
                    }break;
                    case "exch1-order-failed":{
                        handleExch1FailedOrders(message);
                    }break;
                    case "exch2-order-create":{
                        handleExch2CreateOrder(message);
                    }break;
                    case "exch2-order-update":{
                        handleExch2UpdateOrder(message);
                    }break;
                    case "exch2-order-cancel":{
                        handleExch2CanceledOrders(message);
                    }break;
                    case "exch2-order-failed":{
                        handleExch2FailedOrders(message);
                    }break;
                    case "ordervalidator-validation-failed":{
                        handleFailedValidations(message);
                    }break;
                    case "ordervalidator-validation-success":{
                        handleSuccessfulValidations(message);
                    }


                }
                server.getBroadcastOperations().sendEvent("chatevent", message);
            }
        };
        subscribeToChannels(payload);
    }
//  EXCHANGE 1
    private void handleExch1CreateOrder(String message) {
        ExchangeTransaction exchangeTransaction = UtilsComet.convertToObject(message,ExchangeTransaction.class);
        Exchange exchange = MyDao.exchangeDao.findById(1L).get();
        Order order = MyDao.orderDao.findById(exchangeTransaction.getOrder_id()).get();
        StockTransactions stockTransaction = MyDao.stockTransactionsDao.findById(exchangeTransaction.getTransaction_id()).get();
        stockTransaction.setTransaction_status("Pending");
        stockTransaction.setOrder_key(exchangeTransaction.getOrder_key());
        stockTransaction = MyDao.stockTransactionsDao.save(stockTransaction);
        User user = order.getUser();
        this.server.getBroadcastOperations().sendEvent(String.valueOf(user.getUser_id()) + ":created:order",message);
    }

    private void handleExch1UpdateOrder(String message) {
        MonitorRequest monitorRequest = UtilsComet.convertToObject(message,MonitorRequest.class);
        StockTransactions stockTransactions = MyDao.stockTransactionsDao.findById(monitorRequest.getTransaction_id()).get();
        stockTransactions.setCumulatedquantity(monitorRequest.exchangeOrder.getCumulativeQuantity());
        MyDao.stockTransactionsDao.save(stockTransactions);
        User user = stockTransactions.getOrder().getUser();
        this.server.getBroadcastOperations().sendEvent(String.valueOf(user.getUser_id()) + ":updated:order",message);
    }

    private void handleExch1CanceledOrders(String message) {
        OrderRequest orderRequest = UtilsComet.convertToObject(message,OrderRequest.class);
        Order order = MyDao.orderDao.findById(Long.parseLong(orderRequest.getId())).get();
        User user = order.getUser();
        List<StockTransactions> list = order.getStockTransactions().stream().filter(stockTransactions -> stockTransactions.getTransaction_status().equals("Pending")).collect(Collectors.toList());
        if(!list.isEmpty()){
            list.forEach(stockTransactions -> stockTransactions.setTransaction_status("Cancelled"));
            this.server.getBroadcastOperations().sendEvent(String.valueOf(user.getUser_id()) + ":cancelled:order",message);
        }
    }

    private void handleExch1FailedOrders(String message) {
        ExchangeTransaction exchangeTransaction = UtilsComet.convertToObject(message,ExchangeTransaction.class);
        StockTransactions stockTransactions = MyDao.stockTransactionsDao.findById(exchangeTransaction.getTransaction_id()).get();
        stockTransactions.setTransaction_status("Failed");
        User user = stockTransactions.getOrder().getUser();
        this.server.getBroadcastOperations().sendEvent(String.valueOf(user.getUser_id()) + ":failed:order",message);
    }
//EXCHANGE 2
private void handleExch2CreateOrder(String message) {
    ExchangeTransaction exchangeTransaction = UtilsComet.convertToObject(message,ExchangeTransaction.class);
    Exchange exchange = MyDao.exchangeDao.findById(2L).get();
    Order order = MyDao.orderDao.findById(exchangeTransaction.getOrder_id()).get();
    StockTransactions stockTransaction = MyDao.stockTransactionsDao.findById(exchangeTransaction.getTransaction_id()).get();
    stockTransaction.setTransaction_status("Pending");
    stockTransaction.setOrder_key(exchangeTransaction.getOrder_key());
    stockTransaction = MyDao.stockTransactionsDao.save(stockTransaction);
    User user = order.getUser();
    this.server.getBroadcastOperations().sendEvent(String.valueOf(user.getUser_id()) + ":created:order",message);
}

    private void handleExch2UpdateOrder(String message) {
        MonitorRequest monitorRequest = UtilsComet.convertToObject(message,MonitorRequest.class);
        StockTransactions stockTransactions = MyDao.stockTransactionsDao.findById(monitorRequest.getTransaction_id()).get();
        stockTransactions.setCumulatedquantity(monitorRequest.exchangeOrder.getCumulativeQuantity());
        MyDao.stockTransactionsDao.save(stockTransactions);
        User user = stockTransactions.getOrder().getUser();
        this.server.getBroadcastOperations().sendEvent(String.valueOf(user.getUser_id()) + ":updated:order",message);
    }

    private void handleExch2CanceledOrders(String message) {
        OrderRequest orderRequest = UtilsComet.convertToObject(message,OrderRequest.class);
        Order order = MyDao.orderDao.findById(Long.parseLong(orderRequest.getId())).get();
        User user = order.getUser();
        List<StockTransactions> list = order.getStockTransactions().stream().filter(stockTransactions -> stockTransactions.getTransaction_status().equals("Pending")).collect(Collectors.toList());
        if(!list.isEmpty()){
            list.forEach(stockTransactions -> stockTransactions.setTransaction_status("Cancelled"));
            this.server.getBroadcastOperations().sendEvent(String.valueOf(user.getUser_id()) + ":cancelled:order",message);
        }
    }

    private void handleExch2FailedOrders(String message) {
        ExchangeTransaction exchangeTransaction = UtilsComet.convertToObject(message,ExchangeTransaction.class);
        StockTransactions stockTransactions = MyDao.stockTransactionsDao.findById(exchangeTransaction.getTransaction_id()).get();
        stockTransactions.setTransaction_status("Failed");
        User user = stockTransactions.getOrder().getUser();
        this.server.getBroadcastOperations().sendEvent(String.valueOf(user.getUser_id()) + ":failed:order",message);
    }
//    ORDER VALIDATOR

    private void handleSuccessfulValidations(String message) {
        System.out.println("Handle success validations");
        System.out.println(message);
        OrderDetails orderDetails = UtilsComet.convertToObject(message,OrderDetails.class);
        User user = MyDao.userDao.findById(orderDetails.getOrder_id()).get();
        this.server.getBroadcastOperations().sendEvent(String.valueOf(user.getUser_id()) + ":placed:order",message);
    }

    private void handleFailedValidations(String message) {
        OrderDetails orderDetails = UtilsComet.convertToObject(message,OrderDetails.class);
        User user = MyDao.userDao.findById(orderDetails.getOrder_id()).get();
        this.server.getBroadcastOperations().sendEvent(String.valueOf(user.getUser_id()) + ":placed:order",message);
    }
}
