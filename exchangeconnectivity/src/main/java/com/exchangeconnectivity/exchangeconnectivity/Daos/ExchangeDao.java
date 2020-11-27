package com.exchangeconnectivity.exchangeconnectivity.Daos;

import com.exchangeconnectivity.exchangeconnectivity.Dtos.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeDao extends JpaRepository<Exchange,Long> {
}
