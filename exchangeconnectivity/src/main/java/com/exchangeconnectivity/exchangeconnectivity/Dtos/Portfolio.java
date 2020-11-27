package com.exchangeconnectivity.exchangeconnectivity.Dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long port_id;
    @NotNull
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "portfolio")
    private List<PortoflioContent> portfolioContent;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Portfolio() {
    }

    public Portfolio(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PortoflioContent> getPortfolioContent() {
        return portfolioContent;
    }

    public void setPortfolioContent(List<PortoflioContent> portfolioContent) {
        this.portfolioContent = portfolioContent;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "port_id=" + port_id +
                ", name='" + name + '\'' +
                ", portfolioContent=" + portfolioContent +
                ", user=" + user +
                '}';
    }
}
