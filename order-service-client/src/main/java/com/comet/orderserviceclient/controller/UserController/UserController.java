package com.comet.orderserviceclient.controller.UserController;

import com.comet.orderserviceclient.Dtos.Order;
import com.comet.orderserviceclient.Dtos.Portfolio;
import com.comet.orderserviceclient.Dtos.User;
import com.comet.orderserviceclient.Services.OrderService;
import com.comet.orderserviceclient.Services.PortfolioService;
import com.comet.orderserviceclient.Services.UserService;
import com.comet.orderserviceclient.client.OrderClient;
import com.comet.orderserviceclient.soap.bindings.GetOrderRequest;
import com.comet.orderserviceclient.soap.bindings.GetOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    PortfolioService portfolioService;
    @Autowired
    OrderService orderService;

    //register a user here
//    @PostMapping(name = "/register")
//    public User register(@Validated @RequestBody User user){
//       return userService.addUsers(new User(user.getEmail(),user.getName(),user.getPassword()) );
//    }
//
//    @PostMapping(name = "/deleteportfolio/{id}")
//    public void deletePortfolio(@PathVariable Long id){
//        portfolioService.deletePortfolio(id);
//    }
//
//    @PostMapping(name = "/createportfolio")
//    public Portfolio createPortfolio(@Validated @RequestBody Portfolio portfolio){
//        return portfolioService.addPortfolio( new Portfolio(portfolio.getName(),portfolio.getU_id_FK()) );
//    }
//
//    //getting all portfolios per user
//    @GetMapping(name = "/allportfolios")
//    public List<Portfolio> getAllPortfolio(@Validated @RequestBody Portfolio portfolio){
//       return portfolioService.getAllPortfoliosById(portfolio.getU_id_FK());
//    }
//
//    //cancel an order
//    @PostMapping(name = "/cancelorder/{id}")
//    public void cancelledOrder(@PathVariable Long id){
//        orderService.cancelOrderById(id);
//    }

}
