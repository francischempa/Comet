package com.comet.orderserviceclient.Dtos;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "exchange")
public class Exchange {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long exchange_id;
    @NotNull
    private String name;

    public Exchange() {
    }

    public Exchange(Long id, String name) {
        this.exchange_id = id;
        this.name = name;
    }

    public Long getId() {
        return exchange_id;
    }

    public void setId(Long id) {
        this.exchange_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
