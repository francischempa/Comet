package com.exchangeconnectivity.exchangeconnectivity.taskqueues;

import com.exchangeconnectivity.exchangeconnectivity.Dtos.Exchange;
import com.exchangeconnectivity.exchangeconnectivity.Dtos.Order;
import com.exchangeconnectivity.exchangeconnectivity.Dtos.StockTransactions;
import com.exchangeconnectivity.exchangeconnectivity.MyDao;
import com.exchangeconnectivity.exchangeconnectivity.UtilsComet;
import com.exchangeconnectivity.exchangeconnectivity.exchangemodels.ExchangeOrder;
import com.exchangeconnectivity.exchangeconnectivity.exchangemodels.ReadyOrder;
import com.exchangeconnectivity.exchangeconnectivity.responseobjects.ExchangeTransaction;
import com.exchangeconnectivity.exchangeconnectivity.tasks.MonitorRequest;
import reactor.core.publisher.Mono;
import redis.clients.jedis.Jedis;

public class MakeOrderQueue implements Runnable {
    private final Jedis jedis;

    public MakeOrderQueue() {
        this.jedis = new Jedis(UtilsComet.redisAddress);
    }
    @Override
    public void run() {
        while (true){
            String taskKey = UtilsComet.getFromQueue(UtilsComet.service.getMakeOrderQueueKey(),jedis);
            if(taskKey == null) continue;
            String orderString = UtilsComet.getFromQueue(taskKey,jedis);
            ExchangeOrder exchangeOrder = UtilsComet.convertToObject(orderString,ExchangeOrder.class);
            if(exchangeOrder == null) continue;
            System.out.println("#MAKE ORDER QUEUE ");
            System.out.println(exchangeOrder);
            if(exchangeOrder.getPrice() == 0 && exchangeOrder.getQuantity() ==0){
                while (UtilsComet.getQueueLen(taskKey+":id",jedis) > 0){
                    System.out.println(UtilsComet.getFromQueue(taskKey+":id",jedis));
                }
                UtilsComet.deleteData(exchangeOrder.getOrderType(),jedis);
                System.out.println("Clean up done!");
                continue;
            }
            ReadyOrder readyOrder =   new ReadyOrder(exchangeOrder.getId(),exchangeOrder.getProduct(),exchangeOrder.getQuantity(),exchangeOrder.getPrice(),exchangeOrder.getSide());
            String orderId = UtilsComet.exchangeWebClient.post().uri(readyOrder.generateUri().toLowerCase()).body(Mono.just(readyOrder), ReadyOrder.class).retrieve().bodyToMono(String.class).block();
            orderId = UtilsComet.convertToObject(orderId,String.class);
//                (double stock_price, int stock_quantity, String order_key, String transaction_status, Exchange exchange, com.exchangeconnectivity.exchangeconnectivity.Dtos.Order order)
            Exchange exchange = new Exchange();
            if(exchangeOrder.getExchange()=="exch1"){
                exchange = MyDao.exchangeDao.findById(1L).get();
            }else{
                exchange = MyDao.exchangeDao.findById(2L).get();
            }

            Order order = MyDao.orderDao.findById(exchange.getId()).get();
            StockTransactions stockTransactions = new StockTransactions(exchangeOrder.getPrice(),
                    exchangeOrder.getQuantity(),"","",exchange,order);
            stockTransactions = MyDao.stockTransactionsDao.save(stockTransactions);
            Long transaction_id = stockTransactions.getTransaction_id();
            if(isSuccess(orderId)){
                ExchangeTransaction exchangeTransaction = new ExchangeTransaction(transaction_id,Long.parseLong(exchangeOrder.getId()),UtilsComet.service.getId(),orderId,exchangeOrder.getPrice(),exchangeOrder.getQuantity(),"placed successfully");

                UtilsComet.addToQueue(taskKey+":id",orderId,jedis);
                UtilsComet.publish(UtilsComet.service.getId()+"-order-create",exchangeTransaction,jedis);

                MonitorRequest monitorRequest = new MonitorRequest(transaction_id, orderId,exchangeOrder);
                UtilsComet.addToQueue(UtilsComet.service.getOrderMonitor(),UtilsComet.convertToString(monitorRequest),jedis);
                System.out.println("Order placed successfully");

            }else{
                System.out.println("Order Failed");
                ExchangeTransaction exchangeTransaction = new ExchangeTransaction(transaction_id,Long.parseLong(exchangeOrder.getId()),UtilsComet.service.getId(),"",exchangeOrder.getPrice(),exchangeOrder.getQuantity(),"Failed");
                UtilsComet.publish(UtilsComet.service.getId()+"-order-failed",exchangeTransaction,jedis);
            }



        }
    }
    public boolean isSuccess(String response){
        return  response!=null && response.length() < 50;
    }
}
