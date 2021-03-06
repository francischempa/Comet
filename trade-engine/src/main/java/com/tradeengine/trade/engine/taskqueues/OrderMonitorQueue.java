package com.tradeengine.trade.engine.taskqueues;

import com.tradeengine.trade.engine.Config;
import com.tradeengine.trade.engine.UtilsComet;
import com.tradeengine.trade.engine.exchangeconnectivity.ExchConnectivity;
import com.tradeengine.trade.engine.responseobject.ExchangeTransaction;
import com.tradeengine.trade.engine.trademodels.ExchangeOrder;
import com.tradeengine.trade.engine.trademodels.ValidatedOrder;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderMonitorQueue implements Runnable{
    private Jedis jedis;

    public OrderMonitorQueue() {
        this.jedis = new Jedis(Config.getRedisAddress());
    }
    @Override
    public void run() {
        while (true){
            String orderId = UtilsComet.getFromQueue(Config.getOrderMonitorQueue(),jedis);
            if(orderId == null) continue;
            long numberOfResults = UtilsComet.getQueueLen(orderId + "orderbook",jedis);
            if(numberOfResults == Config.numberOfExchanges){ // IS RESPONSE EQUAL THE NUMBER OF EXCHANGES
                System.out.println("#MONITOR QUEUE ");
                String data = UtilsComet.getCacheValue(orderId+"monitor",jedis);
                ValidatedOrder validatedOrder = UtilsComet.convertToObject(data,ValidatedOrder.class);
                UtilsComet.deleteData(orderId+"monitor",jedis);

                List<ExchangeOrder> exchangeOrderList = new ArrayList<>();
                for (int index = 0;index<Config.numberOfExchanges;index++){
                    data = UtilsComet.getFromQueue(orderId + "orderbook",jedis);
                    System.out.println(data);
                    ExchangeOrder[] exchangeOrders = UtilsComet.convertToObject(data,ExchangeOrder[].class);
                    exchangeOrderList.addAll(Arrays.asList(exchangeOrders));
                }
                System.out.println(exchangeOrderList.stream().filter(exchangeOrder -> exchangeOrder.getCumulativeQuantity()>0).collect(Collectors.toList()));
                System.out.println(exchangeOrderList);
                UtilsComet.deleteData(orderId + "orderbook",jedis);

                final double orderPrice = validatedOrder.getPrice();

//                #fixme;
                switch (validatedOrder.getSide().toUpperCase()){
                    case "BUY":{
                        exchangeOrderList = exchangeOrderList.stream().sorted((o1, o2) -> Double.compare(o1.getPrice(),o2.getPrice())).filter(exchangeOrder -> exchangeOrder.getPrice() <= orderPrice).peek(exchangeOrder -> exchangeOrder.setSide("BUY")).collect(Collectors.toList());
                    }break;
                    case "SELL":{
                        exchangeOrderList = exchangeOrderList.stream().sorted((o1, o2) -> Double.compare(o2.getPrice(),o1.getPrice())).filter(exchangeOrder -> exchangeOrder.getPrice() >= orderPrice).peek(exchangeOrder -> exchangeOrder.setSide("SELL")).collect(Collectors.toList());
                    }break;
                    default:{}
                }

//                if(exchangeOrderList.size() == 0){ // NO DEAL AVAILABLE ON ALL EXCHANGES
//                    UtilsComet.addToQueue(Config.getMakeOrderQueueFromOvToTe(),UtilsComet.convertToString(validatedOrder),jedis); // add as a new order from order validator
//                    continue;
//                }

//                #fixme, Enhance using streams
                List<ExchangeOrder> selectedOrders = new ArrayList<>();
                int qtyNeeded = validatedOrder.getQuantity();
                for (ExchangeOrder exchangeOrder : exchangeOrderList) {
                    int availableQty = exchangeOrder.getQuantity() - exchangeOrder.getCumulativeQuantity();
                    if (availableQty >= qtyNeeded) {
                        exchangeOrder.setQuantity(qtyNeeded);
                        selectedOrders.add(exchangeOrder);
                        qtyNeeded = 0;
                        break;
                    }
                    exchangeOrder.setQuantity(availableQty);
                    selectedOrders.add(exchangeOrder);
                    qtyNeeded -= availableQty;
                }

                System.out.println("###1");
//                PROCESS REMAINING ORDER
                if(qtyNeeded>0){
                    ExchangeOrder exchangeOrder = new ExchangeOrder(validatedOrder.getId(),validatedOrder.getProduct(),qtyNeeded,validatedOrder.getPrice(),validatedOrder.getSide(),0);
                    exchangeOrder.setExchange("exch1");
                    selectedOrders.add(exchangeOrder);
                }

//                #fixme, qtyNeeded could be positive (currently ignored)
                System.out.println("###2");
                selectedOrders.forEach(System.out::println);
                System.out.println("Done");
                for (ExchConnectivity exchConnectivity : Config.exchConnectivityList) {
                    List<ExchangeOrder> exchangeSpecificOrders = selectedOrders.stream().filter(exchangeOrder -> exchangeOrder.getExchange().equals(exchConnectivity.getId())).collect(Collectors.toList());
                    List<Double> priceList = exchangeSpecificOrders.stream().map(ExchangeOrder::getPrice).distinct().collect(Collectors.toList());
                    System.out.println("FILTER CHECK");
                    System.out.println(priceList);
                    System.out.println(exchangeSpecificOrders);
                    priceList.forEach(
                            price -> {
                                System.out.println("PRICE CHECK");
                                System.out.println(price);
                                List<ExchangeOrder> exchangeOrders = exchangeSpecificOrders.stream().filter(exchangeOrder -> exchangeOrder.getPrice()==price).collect(Collectors.toList());
                                Optional<Integer> optionalQuantity = exchangeOrders.stream().map(ExchangeOrder::getQuantity).reduce(Integer::sum);
                                if(optionalQuantity.isPresent()){
                                    ExchangeOrder eo = exchangeOrders.get(0);
                                    int quantity = optionalQuantity.get();

//                                    PASS TRANSACTION ID HERE
                                    ExchangeOrder exchangeOrder = new ExchangeOrder(eo.getId(),eo.getProduct(),quantity,eo.getPrice(),eo.getSide(),0);
                                    exchangeOrder.setExchange(exchConnectivity.getId());
                                    UtilsComet.addToQueue(orderId+ ":" +exchConnectivity.getId()+":makeorder",UtilsComet.convertToString(exchangeOrder),jedis);
                                    UtilsComet.addToQueue(exchConnectivity.getMakeOrderQueueKey(),orderId + ":" + exchConnectivity.getId()+":makeorder",jedis);
//                                    Long order_id, Long exchange_id, String order_key, Double stock_price, Integer quantity, String transaction_status
//                                    ADD TO DB HERE
//                                    ExchangeTransaction exchangeTransaction = new ExchangeTransaction(1L,Long.parseLong(orderId),exchConnectivity.getId(),"",price,quantity,"");
//                                    UtilsComet.publish("tradeengine-transaction",exchangeTransaction,jedis);
                                }
                            }
                    );
                    System.out.println("###3");
                    ExchangeOrder exchangeOrder = new ExchangeOrder(validatedOrder.getId(),validatedOrder.getProduct(),0,0,validatedOrder.getSide(),0);
                    exchangeOrder.setExchange(exchConnectivity.getId());
                    UtilsComet.addToQueue(orderId+ ":" +exchConnectivity.getId()+":makeorder",UtilsComet.convertToString(exchangeOrder),jedis);
                    UtilsComet.addToQueue(exchConnectivity.getMakeOrderQueueKey(),orderId + ":" + exchConnectivity.getId()+":makeorder",jedis);

                }
//                while (true){}

            }else{//ALL RESULTS NOT READY; PUT TASK BACK INTO QUEUE
                UtilsComet.addToQueue(Config.getOrderMonitorQueue(),orderId,jedis);
            }
        }
    }
}
