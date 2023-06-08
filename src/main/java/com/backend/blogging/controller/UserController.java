package com.backend.blogging.controller;

import com.backend.blogging.entities.User;
import com.backend.blogging.repository.UserRepository;
import com.backend.blogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user)
    {
        User user1=userService.createUser(user);
        return new ResponseEntity(user1,HttpStatus.CREATED);
    }

    @GetMapping("/getAllUser")
    public  ResponseEntity<List<User>> getAllUsers()
    {
        return new ResponseEntity(userService.getAllUser(),HttpStatus.OK);
    }

    @PutMapping("user/update/{userId}")
    public ResponseEntity<User>  updateUser(@PathVariable int userId,@RequestBody User user)
    {
        return new ResponseEntity<>(userService.updateUser(user,userId),HttpStatus.OK);
    }

    @DeleteMapping("user/delete/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable int userId) throws Exception {
        userService.deleteUser(userId);
    }

    @PatchMapping("updatePassword/{userId}")
    public void updatePassword(@PathVariable int userId,@RequestBody User user)
    {
        userService.updatePassword(user,userId);
    }

    @GetMapping("/user/getUserById/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId) throws Exception
    {
        ResponseEntity<User> response=new ResponseEntity<>(HttpStatus.ACCEPTED);
        if(userRepository.findById(userId).isPresent()) {
            response = new ResponseEntity(userService.getSingleUser(userId), HttpStatus.OK);
        }
        else{
            System.out.println("Id is not present");
        }
        return response;
    }


}
