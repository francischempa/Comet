package com.comet.orderserviceclient.Daos;

import com.comet.orderserviceclient.Dtos.StockTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StockTransactionsDao extends JpaRepository<StockTransactions,Long> {

    @Query("select transaction_id,transaction_status,stock_price,stock_quantity from StockTransactions where transaction_status = 'pending' ")
    List<StockTransactions> getOpenOrders();

    @Query("select transaction_id,transaction_status,stock_price,stock_quantity from StockTransactions where transaction_status = 'closed' ")
    List<StockTransactions> getClosedOrders();

    @Query("select transaction_id,transaction_status,stock_price,stock_quantity from StockTransactions where transaction_status = 'cancelled'")
    List<StockTransactions> getCancelledOrders();

}
