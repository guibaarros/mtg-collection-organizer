package com.mtgcollectionorganizer.api.user.controller;

import com.mtgcollectionorganizer.api.user.controller.dto.UserDTO;
import com.mtgcollectionorganizer.api.user.controller.dto.UserLoginDTO;
import com.mtgcollectionorganizer.api.user.controller.dto.UserPasswordDTO;
import com.mtgcollectionorganizer.api.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(@RequestBody final UserDTO userDto){
        return userService.addUser(userDto);
    }

    @PatchMapping("/{user_id}/")
    @ResponseStatus(HttpStatus.CREATED)
    public void changePassword(@RequestBody final UserPasswordDTO userPasswordDto,
                               @PathVariable("user_id") final String userId //TODO receber de outra forma
                               )
    {
        userService.changePassword(userId, userPasswordDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean login(@RequestBody final UserLoginDTO userLoginDto){
        return userService.login(userLoginDto.getUserName(), userLoginDto.getPassword());
    }
}
