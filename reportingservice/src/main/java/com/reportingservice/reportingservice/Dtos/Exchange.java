package com.reportingservice.reportingservice.Dtos;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "exchange")
public class Exchange {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long exchange_id;
    @NotNull
    private String name;

    @OneToMany(mappedBy = "exchange")
    private List<StockTransactions> stockTransactions;

    public Exchange() {
    }

    public Exchange(String name) {
        this.name = name;
    }

    public Exchange(String name, List<StockTransactions> stockTransactions) {
        this.name = name;
        this.stockTransactions = stockTransactions;
    }

    public Long getId() {
        return exchange_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StockTransactions> getStockTransactions() {
        return stockTransactions;
    }

    public void setStockTransactions(List<StockTransactions> stockTransactions) {
        this.stockTransactions = stockTransactions;
    }

    @Override
    public String toString() {
        return "Exchange{" +
                "exchange_id=" + exchange_id +
                ", name='" + name + '\'' +
                ", stockTransactions=" + stockTransactions +
                '}';
    }
}
