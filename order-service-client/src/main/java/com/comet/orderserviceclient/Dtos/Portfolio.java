package com.comet.orderserviceclient.Dtos;

import com.sun.istack.NotNull;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.util.List;

@Entity
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long port_id;
    @NotNull
    private String name;
    @NotNull
    private Long u_id_FK;

    @OneToMany(targetEntity = PortoflioContent.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "port_id_FK", referencedColumnName = "port_id")
    private List<PortoflioContent> portfolioContent;



    public Portfolio() {
    }

    public Portfolio(String name, Long u_id_FK) {
        this.name = name;
        this.u_id_FK = u_id_FK;
    }

    public Long getPort_id() {
        return port_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getU_id_FK() {
        return u_id_FK;
    }

    public void setU_id_FK(Long u_id_FK) {
        this.u_id_FK = u_id_FK;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "port_id=" + port_id +
                ", name='" + name + '\'' +
                ", u_id_FK=" + u_id_FK +
                '}';
    }
}
