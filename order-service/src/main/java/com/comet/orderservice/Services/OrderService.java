package com.comet.orderservice.Services;

import com.comet.orderservice.Dao.OrderDao;
import com.comet.orderservice.Dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    public Order addNewOrders(Order order){
        return orderDao.save(order);
    }
}
