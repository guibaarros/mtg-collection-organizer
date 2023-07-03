package com.mtgcollectionorganizer.api.user.collection.controller;

import com.mtgcollectionorganizer.api.user.collection.controller.dto.UserCardDTO;
import com.mtgcollectionorganizer.api.user.collection.service.UserCardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{user_id}/card")
@AllArgsConstructor
public class UserCardController {

    private final UserCardService cardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addCardToUser(@RequestBody final UserCardDTO userCardDTO,
                              @PathVariable("user_id") final String userId){
        return cardService.addCard(userCardDTO, userId);
    }
}
