package com.backend.blogging.controller;

import com.backend.blogging.entities.User;
import com.backend.blogging.exception.ApiExceptiom;
import com.backend.blogging.payload.JwtAuthRequest;
import com.backend.blogging.payload.JwtAuthResponse;
import com.backend.blogging.security.JwtTokenHelper;
import com.backend.blogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth/")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
        this.authenticate(request.getEmail(),request.getPassword());
        UserDetails userDetails=this.userDetailsService.loadUserByUsername(request.getEmail());
        String token=this.jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse response=new JwtAuthResponse();
        response.setAuthenticationToken(token);
        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
    }

    private void authenticate(String email, String password) throws Exception {
        UsernamePasswordAuthenticationToken upat=new UsernamePasswordAuthenticationToken(email,password);
             try {
                 this.authenticationManager.authenticate(upat);
             }
             catch (BadCredentialsException e)
             {

                 throw new ApiExceptiom("Invalid user name and password!!!.");
             }
    }

    @PostMapping("/allRegister")
    public ResponseEntity<User> registerUsers(@RequestBody User user)
    {
        User saveUser=new User();
        saveUser=this.userService.saveUser(user);
        return new ResponseEntity<>(saveUser,HttpStatus.OK);
    }

}
