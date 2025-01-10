package com.ma.oracle.DTO;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String role;
    private String tablespace;
    private int quotaMb ;
}
