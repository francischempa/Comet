package com.comet.orderserviceclient.Daos;

import com.comet.orderserviceclient.Dtos.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeDao extends JpaRepository<Exchange,Long> {
}
