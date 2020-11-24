package com.comet.orderserviceclient.Services;

import com.comet.orderserviceclient.Daos.UserDao;
import com.comet.orderserviceclient.Dtos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User addUsers(User user){
//        final String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        Optional<User> optionalUser = userDao.findUserByEmail(user.getEmail());
        if(!optionalUser.isPresent()){
            userDao.save(user);
        }
        return user;
    }

    public List<User> getAllUsers(){
        return userDao.findAll();
    }

    public User updateUser(User user){
        return null;
//        userDao.findById(user.getUser_id());
//        User usertobeUpdated = new User();
//        userDao.findById(id).map(user -> {
//            user.setName(usertobeUpdated.getName());
//            user.setEmail(usertobeUpdated.getEmail());
//            user.setPassword(usertobeUpdated.getPassword());
//            return userDao.save(usertobeUpdated);
//        }).orElseGet(() -> {
//            usertobeUpdated.setUser_id(id);
//            return userDao.save(usertobeUpdated);
//        });
//        return usertobeUpdated;
    }


    public void deleteClient(Long id){
        userDao.deleteById(id);
    }
}
