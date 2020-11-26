package com.comet.orderservice.Dao;

import com.comet.orderservice.Dto.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductDao extends JpaRepository<Product,Long> {
//    @Query("select product_id,product_name from Product where product_name = :name")
    Optional<Product> getProductByProductName(String name);
}
