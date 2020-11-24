package com.comet.orderserviceclient.controller.UserController;

import com.comet.orderserviceclient.Dtos.Order;
import com.comet.orderserviceclient.Services.OrderService;
import com.comet.orderserviceclient.client.OrderClient;
import com.comet.orderserviceclient.soap.bindings.GetOrderRequest;
import com.comet.orderserviceclient.soap.bindings.GetOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderClient orderClient;
    @Autowired
    OrderService orderService;

    @PostMapping("/order")
    public GetOrderResponse showDetails (@RequestBody GetOrderRequest getOrderRequest){
        return orderClient.showRequestDetails(getOrderRequest);
    }
}
