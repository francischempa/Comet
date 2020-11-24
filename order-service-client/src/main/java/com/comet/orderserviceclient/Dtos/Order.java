package com.comet.orderserviceclient.Dtos;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long order_id;
    @NotNull
    private int product_id;
    @NotNull
    private String side;
    @NotNull
    private double order_price;
    @NotNull
    private int quantity;

    public Order() {
    }

    public Order(int product_id, String side, double order_price, int quantity) {
        this.product_id = product_id;
        this.side = side;
        this.order_price = order_price;
        this.quantity = quantity;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public int getProduct_id_FK() {
        return product_id;
    }

    public void setProduct_id_FK(int product_id_FK) {
        this.product_id = product_id_FK;
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

    @Override
    public String toString() {
        return "Order{" +
                "order_id=" + order_id +
                ", product_id=" + product_id +
                ", side='" + side + '\'' +
                ", order_price=" + order_price +
                ", quantity=" + quantity +
                '}';
    }
}
