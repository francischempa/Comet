package com.exchangeconnectivity.exchangeconnectivity.Daos;

import com.exchangeconnectivity.exchangeconnectivity.Dtos.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PortfolioDao extends JpaRepository<Portfolio,Long> {

    @Query("SELECT name FROM Portfolio WHERE user = :user_id")
    List<Portfolio> getAllUserPortfolio(@Param("user_id") Long id);
}
