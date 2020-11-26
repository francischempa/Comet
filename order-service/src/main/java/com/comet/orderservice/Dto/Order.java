package com.comet.orderservice.Dto;

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
    private Double order_price;
    @NotNull
    private Integer quantity;
    @NotNull
    private LocalDateTime placedOn;

    @NotNull
    @Column(name = "product_id")
    private Long  productId;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    public Order() {
    }

    public Order(String side, Double order_price, Integer quantity, Long productId, Long userId) {
        this.side = side;
        this.order_price = order_price;
        this.quantity = quantity;
        this.productId = productId;
        this.userId = userId;
        this.placedOn = LocalDateTime.now();
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public Double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(Double order_price) {
        this.order_price = order_price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
