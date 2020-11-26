package com.comet.orderservice.Dao;

import com.comet.orderservice.Dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User,Long> {
}
