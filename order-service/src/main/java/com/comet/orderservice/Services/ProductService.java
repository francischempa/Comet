package com.comet.orderservice.Services;

import com.comet.orderservice.Dao.ProductDao;
import com.comet.orderservice.Dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductDao productDao;

    public Optional<Product> getProductByTicker(String name){
        return productDao.getProductByProductName(name);
    }
}
