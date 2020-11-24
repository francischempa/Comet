package com.comet.orderserviceclient.controller.AdminController;

import com.comet.orderserviceclient.Dtos.Exchange;
import com.comet.orderserviceclient.Dtos.User;
import com.comet.orderserviceclient.Services.ExchangeService;
import com.comet.orderserviceclient.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ExchangeService exchangeService;

    @Autowired
    UserService userService;

    @GetMapping("/allexchanges")
    public List<Exchange> getAllExchange(){
        return exchangeService.allActiveExchange();
    }

    @GetMapping("/allusers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
}
