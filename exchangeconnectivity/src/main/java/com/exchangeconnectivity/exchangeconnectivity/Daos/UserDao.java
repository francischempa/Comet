package com.exchangeconnectivity.exchangeconnectivity.Daos;
//
import com.exchangeconnectivity.exchangeconnectivity.Dtos.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDao extends JpaRepository<User,Long> {
}
