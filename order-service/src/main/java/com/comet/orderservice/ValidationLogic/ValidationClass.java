package com.comet.orderservice.ValidationLogic;

import com.comet.orderservice.Dao.UserDao;
import com.comet.orderservice.Dto.User;
import com.comet.orderservice.marketdata.MarketData;
import comet.com.make_order_request.GetOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ValidationClass implements Validated{


    @Override
    public boolean validate(GetOrderRequest request, User user, MarketData marketData) {
        double difference = Math.abs(Double.parseDouble(request.getPrice()) - marketData.getLAST_TRADED_PRICE() );
        double thresholdPrice = 3;

        //check customer's balance!!!
        double incomingbalance = Integer.parseInt(request.getQuantity()) * Double.parseDouble(request.getPrice());
        if(user.getBalance() < incomingbalance){
            return  false;
        }
        if(request.getSide().equals("BUY")){
            if(Integer.parseInt(request.getQuantity()) <= 0 || Integer.parseInt(request.getQuantity()) > marketData.getBUY_LIMIT() ){
                System.out.println("There's a problem with your quantity!");
                return false;
            }else if( difference >= thresholdPrice || difference <= 0){
                System.out.println("Consider reducing your waging Bid price or check your waging price");
                return false;
            }
        } else if(request.getSide().equals("SELL")){
            if(Integer.parseInt(request.getQuantity()) <= 0 || Integer.parseInt(request.getQuantity()) > marketData.getSELL_LIMIT() ){
                System.out.println("There's a problem with your quantity!");
                return false;
            }else if( difference >= thresholdPrice || difference <= 0){
                System.out.println("Consider reducing your waging Ask price or check your waging price");
                return false;
            }
        }
        return true;
    }
}
