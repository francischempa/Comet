package com.reportingservice.reportingservice.Daos;

import com.reportingservice.reportingservice.Dtos.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeDao extends JpaRepository<Exchange,Long> {
}
