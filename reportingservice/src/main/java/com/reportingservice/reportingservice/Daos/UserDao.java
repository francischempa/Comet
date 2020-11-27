package com.reportingservice.reportingservice.Daos;
//
import com.reportingservice.reportingservice.Dtos.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserDao extends JpaRepository<User,Long> {
}
