package com.reportingservice.reportingservice.Dtos;

import javax.persistence.*;

@Entity
public class PortoflioContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long portfolio_content_id;
    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "port_id")
    private Portfolio portfolio;

    public PortoflioContent() {
    }

    public PortoflioContent(int quantity, Product product, Portfolio portfolio) {
        this.quantity = quantity;
        this.product = product;
        this.portfolio = portfolio;
    }

    public Long getPortfolio_content_id() {
        return portfolio_content_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    @Override
    public String toString() {
        return "PortoflioContent{" +
                "portfolio_content_id=" + portfolio_content_id +
                ", quantity=" + quantity +
                ", product=" + product +
                ", portfolio=" + portfolio +
                '}';
    }
}
