package com.mtgcollectionorganizer.api.user.controller.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UserDTO {
    private String id;
    private String displayName;
    private String userName;
    private String email;
    private List<String> roles;
    private String password;
}
