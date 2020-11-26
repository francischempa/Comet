package com.comet.orderserviceclient.Dtos;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;
    @NotNull
    private String side;
    @NotNull
    private double order_price;
    @NotNull
    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<StockTransactions> stockTransactions;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    private LocalDateTime placedOn;

    public Order() {
    }

    public Order(String side, double order_price, int quantity) {
        this.side = side;
        this.order_price = order_price;
        this.quantity = quantity;
        this.placedOn = LocalDateTime.now();
    }


    public Order(String side, double order_price, int quantity, User user, List<StockTransactions> stockTransactions, Product product) {
        this.side = side;
        this.order_price = order_price;
        this.quantity = quantity;
        this.user = user;
        this.stockTransactions = stockTransactions;
        this.product = product;
        this.placedOn = LocalDateTime.now();
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(double order_price) {
        this.order_price = order_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<StockTransactions> getStockTransactions() {
        return stockTransactions;
    }

    public void setStockTransactions(List<StockTransactions> stockTransactions) {
        this.stockTransactions = stockTransactions;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Order{" +
                "order_id=" + order_id +
                ", side='" + side + '\'' +
                ", order_price=" + order_price +
                ", quantity=" + quantity +
                ", user=" + user +
                ", stockTransactions=" + stockTransactions +
                ", product=" + product +
                '}';
    }
}
