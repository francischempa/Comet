package com.comet.orderserviceclient.Daos;

import com.comet.orderserviceclient.Dtos.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserDao extends JpaRepository<User,Long> {

    Optional<User> findUserByEmail(String email);
}
