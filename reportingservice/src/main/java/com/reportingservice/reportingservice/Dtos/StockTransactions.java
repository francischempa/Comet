package com.reportingservice.reportingservice.Dtos;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class StockTransactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaction_id;
    @NotNull
    private double stock_price;
    @NotNull
    private int stock_quantity;
    @NotNull
    private String order_key;
    private String transaction_status;

    @NotNull
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exchange_id")
    private Exchange exchange;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    public StockTransactions() {
    }

    public StockTransactions(double stock_price, int stock_quantity, String order_key, String transaction_status) {
        this.stock_price = stock_price;
        this.stock_quantity = stock_quantity;
        this.order_key = order_key;
        this.transaction_status = transaction_status;
        this.dateTime = LocalDateTime.now();
    }

    public Long getTransaction_id() {
        return transaction_id;
    }

    public double getStock_price() {
        return stock_price;
    }

    public void setStock_price(double stock_price) {
        this.stock_price = stock_price;
    }

    public int getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public String getOrder_key() {
        return order_key;
    }

    public void setOrder_key(String order_key) {
        this.order_key = order_key;
    }

    public String getTransaction_status() {
        return transaction_status;
    }

    public void setTransaction_status(String transaction_status) {
        this.transaction_status = transaction_status;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "StockTransactions{" +
                "transaction_id=" + transaction_id +
                ", stock_price=" + stock_price +
                ", stock_quantity=" + stock_quantity +
                ", order_key='" + order_key + '\'' +
                ", transaction_status='" + transaction_status + '\'' +
                '}';
    }
}
