package com.backend.blogging.payload;

import lombok.Data;
import lombok.Getter;

@Data
public class JwtAuthRequest {

    private String email;
    private String password;


}
