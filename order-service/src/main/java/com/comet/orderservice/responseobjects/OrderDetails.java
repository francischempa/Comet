package com.comet.orderservice.responseobjects;

public class OrderDetails {
    private Long order_id;
    private Double order_price;
    private String product;
    private Integer quantity;
    private String side;

    public OrderDetails() {
    }

    public OrderDetails(Long order_id, Double order_price, String product, Integer quantity, String side) {
        this.order_id = order_id;
        this.order_price = order_price;
        this.product = product;
        this.quantity = quantity;
        this.side = side;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(Double order_price) {
        this.order_price = order_price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "order_id=" + order_id +
                ", order_price=" + order_price +
                ", product_id=" + product +
                ", quantity=" + quantity +
                ", side='" + side + '\'' +
                '}';
    }
}


