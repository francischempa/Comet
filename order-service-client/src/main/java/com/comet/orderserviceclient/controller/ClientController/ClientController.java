package com.comet.orderserviceclient.controller.ClientController;
import com.comet.orderserviceclient.Dtos.Order;
import com.comet.orderserviceclient.Dtos.Portfolio;
import com.comet.orderserviceclient.Dtos.User;
import com.comet.orderserviceclient.Services.OrderService;
import com.comet.orderserviceclient.Services.PortfolioService;
import com.comet.orderserviceclient.Services.UserService;
import com.comet.orderserviceclient.client.OrderClient;
import com.comet.orderserviceclient.soap.bindings.GetOrderRequest;
import com.comet.orderserviceclient.soap.bindings.GetOrderResponse;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ClientController {
    @Autowired
    UserService userService;
    @Autowired
    PortfolioService portfolioService;
    @Autowired
    OrderService orderService;


    @CrossOrigin
    @PostMapping(path = "/register")
    public User register(@Validated @RequestBody User user,Portfolio portfolio){
        User user1 = userService.addUsers(new User(user.getName(),user.getEmail(),user.getPassword()) );
//        create a default portfolio
        portfolio.setUser(user1);
        portfolio.setName("Default");
        portfolioService.addPortfolio(portfolio);
       return user1;
    }

    @CrossOrigin
    @PostMapping(path = "/login")
    public User login(@Validated @RequestBody User user){
        User user1 = userService.getUserById(user.getUser_id()).get();
        if(user1.getEmail().equals(user.getEmail()) && user1.getPassword().equals(user.getPassword())){
            return user;
        }
        return new User();
    }

    @CrossOrigin
    @PostMapping(path = "/deleteportfolio/{id}")
    public void deletePortfolio(@PathVariable Long id){
        portfolioService.deletePortfolio(id);
    }

    @CrossOrigin
    @PostMapping(path = "/createportfolio")
    public Portfolio createPortfolio(@Validated @RequestBody Portfolio portfolio){
        Optional<User> optionalUser = userService.getUserById(portfolio.getUser().getUser_id());
        if(optionalUser.isPresent()) {
            portfolio.setUser(optionalUser.get());
            return portfolioService.addPortfolio(portfolio);
        }
        return new Portfolio();
    }

    //getting all portfolios per user
    @CrossOrigin
    @GetMapping(path = "/allportfolios")
    public List<Portfolio> getAllPortfolio(@Validated @RequestBody Portfolio portfolio){
       return portfolioService.getAllPortfolios();
    }

    //cancel an order
    @PostMapping(path = "/cancelorder/{id}")
    public void cancelledOrder(@PathVariable Long id){
        orderService.cancelOrderById(id);
    }

}
