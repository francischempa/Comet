package com.comet.orderservice.endpoint;

import com.comet.orderservice.ValidationLogic.*;
import com.comet.orderservice.marketdata.MarketData;
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

        ObjectMapper objectMapper = new ObjectMapper();
        try {
           marketData = objectMapper.readValue(currentMarketData, MarketData.class);
        }catch (JsonGenerationException e){
            e.printStackTrace();    
        }

        String requestStr="";
        requestStr = String.format("{\"id\":\"%s\",\"product\":\"%s\",\"quantity\":%d,\"price\":%f,\"side\":\"%s\"}",
                request.getId(),request.getProduct(),Integer.parseInt(request.getQuantity()),Double.parseDouble(request.getPrice()),request.getSide());

        Validated valid = new ValidationClass();
        if( valid.validate(request,marketData) ){
            jedis.set(request.getId(), requestStr);
            jedis.lpush("queue:ov:te:makeorder", request.getId());
        }
        return response;
    }
}

