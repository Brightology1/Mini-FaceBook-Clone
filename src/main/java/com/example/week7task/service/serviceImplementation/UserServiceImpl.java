package com.example.week7task.service.serviceImplementation;

import com.example.week7task.model.User;
import com.example.week7task.repository.UserRepository;
import com.example.week7task.service.UserService;
import com.example.week7task.utils.PasswordHashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public boolean createUser(User user){
        boolean flag = false;
        user.setPassword(PasswordHashing.encryptPassword(user.getPassword()));
        User userData = userRepository.findPersonByEmail(user.getEmail());
        if(userData != null){
            if(userData == null){
                System.out.println(user);
                userRepository.save(user);
                flag = true;
            }
        }
        return  flag;
    }

    public User getUser(String email, String password){

        User userData = null;

        try {

            userData = userRepository.findPersonByEmail(email);

            if(!password.equals(PasswordHashing.decryptPassword(userData.getPassword()))){
                userData = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userData;
    }
}
