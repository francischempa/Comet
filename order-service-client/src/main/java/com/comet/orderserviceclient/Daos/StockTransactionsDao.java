package com.comet.orderserviceclient.Daos;

import com.comet.orderserviceclient.Dtos.StockTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockTransactionsDao extends JpaRepository<StockTransactions,Long> {

    @Query("select transaction_id from StockTransactions where transaction_status = 'pending' ")
    List<StockTransactions> getOpenOrders();
}
