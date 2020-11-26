package com.comet.orderservice.Services;

import com.comet.orderservice.Dao.UserDao;
import com.comet.orderservice.Dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public Optional<User> getUserById(Long id){
        Optional<User> u =  userDao.findById(id);
        System.out.println(u);
        System.err.println(u);
        return u;
    }
}
