package com.comet.orderservice.Dao;

import com.comet.orderservice.Dto.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order,Long> {
}
