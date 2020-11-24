package com.comet.orderserviceclient.Daos;

import com.comet.orderserviceclient.Dtos.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PortfolioDao extends JpaRepository<Portfolio,Long> {

    @Query("SELECT name FROM Portfolio WHERE u_id_FK = :user_id")
    List<Portfolio> getAllUserPortfolio(@Param("user_id") Long id);
}
