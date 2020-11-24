package com.comet.orderservice.ValidationLogic;

import com.comet.orderservice.marketdata.MarketData;
import comet.com.make_order_request.GetOrderRequest;

public interface Validated {
    boolean validate(GetOrderRequest request, MarketData marketData);
}
