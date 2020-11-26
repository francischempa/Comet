package com.comet.orderserviceclient.controller.AdminController;

import com.comet.orderserviceclient.Daos.OrderDao;
import com.comet.orderserviceclient.Daos.StockTransactionsDao;
import com.comet.orderserviceclient.Daos.UserDao;
import com.comet.orderserviceclient.Dtos.Exchange;
import com.comet.orderserviceclient.Dtos.Order;
import com.comet.orderserviceclient.Dtos.StockTransactions;
import com.comet.orderserviceclient.Dtos.User;
import com.comet.orderserviceclient.Services.ExchangeService;
import com.comet.orderserviceclient.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ExchangeService exchangeService;
    @Autowired
    UserService userService;
    @Autowired
    StockTransactionsDao stockTransactionsDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    UserDao userDao;

    @GetMapping(path = "/allexchanges")
    public List<Exchange> getAllExchange(){
        return exchangeService.allActiveExchange();
    }

    @GetMapping(path = "/allusers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(path = "/getopenorders")
    public List<StockTransactions> getOpenOrders(){
        return stockTransactionsDao.getOpenOrders();
    }

    @GetMapping(path = "/getclosedorders")
    public List<StockTransactions> getClosedOrders(){
        return stockTransactionsDao.getClosedOrders();
    }

    @GetMapping(path = "/allorders")
    public List<Order> getAllOrders(){
        return orderDao.findAll();
    }

    @GetMapping(path = "/cancelledorders")
    public List<StockTransactions> getCancelledOrders(){
        return stockTransactionsDao.getCancelledOrders();
    }

    @PostMapping(path = "/deleteuser/{id}")
        public void deleteUser(@RequestParam Long id){
            userDao.deleteById(id);
    }

}
