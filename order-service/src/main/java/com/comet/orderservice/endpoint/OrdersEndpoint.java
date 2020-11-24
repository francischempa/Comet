package com.comet.orderservice.endpoint;

import com.comet.orderservice.ValidationLogic.*;
import com.comet.orderservice.marketdata.MarketData;
import com.comet.orderservice.responseobjects.OrderDetails;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import comet.com.make_order_request.GetOrderRequest;
import comet.com.make_order_request.GetOrderResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import redis.clients.jedis.Jedis;


@Endpoint
public class OrdersEndpoint {
    private static final String NAMESPACE_URI = "http://com.comet/make-order-request";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOrderRequest")
    @ResponsePayload
    public GetOrderResponse showRequestDetails(@RequestPayload GetOrderRequest request) throws JsonProcessingException {
        GetOrderResponse response=  new GetOrderResponse();
        response.setOrder(response.getOrder());

        String ticker = "md:"+request.getProduct();
        Jedis jedis = new Jedis("192.168.128.253");

        String currentMarketData = jedis.get(ticker);
        MarketData marketData = new MarketData();
//        (Long order_id, Double order_price, Long product_id, Integer quantity, String side)
        OrderDetails orderDetails = new OrderDetails(
                Long.parseLong(request.getId()),
                Double.parseDouble(request.getPrice()),request.getProduct(),
                Integer.parseInt(request.getQuantity()),request.getSide());
        String orders = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
           marketData = objectMapper.readValue(currentMarketData, MarketData.class);
           orders = objectMapper.writeValueAsString(orderDetails);
        }catch (JsonGenerationException e){
            e.printStackTrace();
        }

        String requestStr="";
        requestStr = String.format("{\"id\":\"%s\",\"product\":\"%s\",\"quantity\":%d,\"price\":%f,\"side\":\"%s\"}",
                request.getId(),request.getProduct(),Integer.parseInt(request.getQuantity()),Double.parseDouble(request.getPrice()),request.getSide());

        System.out.println(requestStr);
        Validated valid = new ValidationClass();
        if( valid.validate(request,marketData) ){
            jedis.set(request.getId(), requestStr);
            jedis.lpush("queue:ov:te:makeorder", request.getId());
            System.out.println("We validated order ");
            jedis.publish("ordervalidator-validation-success", "Order Validated"+ orders);
        }
        return response;


    }
}

