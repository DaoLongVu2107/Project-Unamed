package com.itboy.DACNPM.Service;

import com.itboy.DACNPM.Enity.User;
import com.itboy.DACNPM.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserSevice {
    @Autowired
    public UserRepository userRepository;
    public User createUser(User user){
        ///
        return userRepository.save(user) ;
    }
    public Optional<User> finUserby(int id){
        return userRepository.findById(id);
    }
    public List<User> findallUser(){
        return userRepository.findAll();
    }
    public int check(String email,String pass){
        List<User> userList=  userRepository.findAll();

        for(User user:userList){
            if(email.equals(user.getEmail())&&pass.equals(user.getPassword())){
                return user.getIdUser();
            }
        }

        return 0;
    }


}