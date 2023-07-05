package com.mtgcollectionorganizer.api.user.controller;

import com.mtgcollectionorganizer.api.security.LoginService;
import com.mtgcollectionorganizer.api.user.controller.dto.UserDTO;
import com.mtgcollectionorganizer.api.user.controller.dto.UserLoginDTO;
import com.mtgcollectionorganizer.api.user.controller.dto.UserPasswordDTO;
import com.mtgcollectionorganizer.api.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final LoginService loginService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(@RequestBody final UserDTO userDto){
        return userService.addUser(userDto);
    }

    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.CREATED)
    public void changePassword(@RequestBody final UserPasswordDTO userPasswordDto,
                               @RequestHeader("Authorization") String authorizationHeader
                               ) {
        userService.changePassword(loginService.getUsernameByToken(authorizationHeader), userPasswordDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public String login(@RequestBody final UserLoginDTO userLoginDto){
        return loginService.login(userLoginDto.getUserName(), userLoginDto.getUserName().concat(userLoginDto.getPassword()));
    }

    @GetMapping("/getUserByToken")
    @ResponseStatus(HttpStatus.CREATED)
    public String getUserByToken(@RequestHeader("Authorization") String authorizationHeader){
        return loginService.getUsernameByToken(authorizationHeader);
    }
}
