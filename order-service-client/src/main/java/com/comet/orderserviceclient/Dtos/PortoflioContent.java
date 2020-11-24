package com.comet.orderserviceclient.Dtos;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class PortoflioContent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long portfolio_content_id;
    private int quantity;
    private int port_id_FK;
    private Long product_id_FK;

    public PortoflioContent() {
    }

    @ManyToOne(targetEntity = Product.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    public PortoflioContent(Long portfolio_content_id, int quantity, int port_id_FK, Long product_id_FK) {
        this.portfolio_content_id = portfolio_content_id;
        this.quantity = quantity;
        this.port_id_FK = port_id_FK;
        this.product_id_FK = product_id_FK;
    }

    public Long getPortfolio_content_id() {
        return portfolio_content_id;
    }

    public void setPortfolio_content_id(Long portfolio_content_id) {
        this.portfolio_content_id = portfolio_content_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPort_id_FK() {
        return port_id_FK;
    }

    public void setPort_id_FK(int port_id_FK) {
        this.port_id_FK = port_id_FK;
    }

    public Long getProduct_id_FK() {
        return product_id_FK;
    }

    public void setProduct_id_FK(Long product_id_FK) {
        this.product_id_FK = product_id_FK;
    }

    @Override
    public String toString() {
        return "PortoflioContent{" +
                "portfolio_content_id=" + portfolio_content_id +
                ", quantity=" + quantity +
                ", port_id_FK=" + port_id_FK +
                ", product_id_FK=" + product_id_FK +
                '}';
    }
}
