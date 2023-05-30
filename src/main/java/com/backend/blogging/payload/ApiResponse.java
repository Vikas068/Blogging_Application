package com.backend.blogging.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    private String message;
    private boolean status;
}
