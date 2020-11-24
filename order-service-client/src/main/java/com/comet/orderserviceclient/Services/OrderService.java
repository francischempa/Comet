package com.comet.orderserviceclient.Services;

import com.comet.orderserviceclient.Daos.OrderDao;
import com.comet.orderserviceclient.Dtos.Order;
import com.comet.orderserviceclient.soap.bindings.GetOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    public void cancelOrderById(Long id){
        orderDao.deleteById(id);
    }

    public List<Order> addNewOrders(Order order){
        return (List<Order>) orderDao.save(order);
    }
}
