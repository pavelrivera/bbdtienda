package com.example.demo.security.dto;


import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfoResponse {

    private Long id;

    private String username;

    private String email;

    private List<String> roles= new ArrayList<>();


    
}
