package com.reportingservice.reportingservice.Daos;

import com.comet.orderserviceclient.Dtos.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order,Long> {
}
