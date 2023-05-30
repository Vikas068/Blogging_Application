package com.backend.blogging.service;

import com.backend.blogging.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    User createUser(User user);

    User updateUser(User user,int userId);

    void deleteUser(int userId) throws Exception;

    List<User> getAllUser();

    User getSingleUser(int userId) throws Exception;

    User updatePassword(User user,int userId);




}
