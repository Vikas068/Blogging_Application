package com.backend.blogging.serviceimplementation;

import com.backend.blogging.entities.User;
import com.backend.blogging.repository.UserRepository;
import com.backend.blogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;


    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user, int userId) {

        User saveUser=userRepository.findById(userId).get();
        saveUser.setAbout(user.getAbout());
        saveUser.setName(user.getName());
        saveUser.setEmail(user.getEmail());
        saveUser.setPosts(user.getPosts());
        saveUser.setRole(user.getRole());
        User returnUser=this.userRepository.save(saveUser);
        return returnUser;
    }

    @Override
    public User updatePassword(User user,int userId)
    {
        User saveUser=userRepository.findById(userId).orElse(null);
        saveUser.setPassword(user.getPassword());
        User returnUpdate=userRepository.save(saveUser);
        return returnUpdate;
    }

    @Override
    public void deleteUser(int userId) throws Exception {
        User user=userRepository.findById(userId).orElseThrow(()->new Exception("User id is not found."));
        userRepository.deleteById(userId);
        }
    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getSingleUser(int userId) {
        return userRepository.findById(userId).get();
    }
}
