package com.comet.orderserviceclient.Daos;

import com.comet.orderserviceclient.Dtos.Order;
import com.comet.orderserviceclient.Dtos.StockTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderDao extends JpaRepository<Order,Long> {
}
