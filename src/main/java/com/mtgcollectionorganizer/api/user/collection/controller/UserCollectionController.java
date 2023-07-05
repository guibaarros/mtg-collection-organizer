package com.mtgcollectionorganizer.api.user.collection.controller;

import com.mtgcollectionorganizer.api.security.LoginService;
import com.mtgcollectionorganizer.api.user.collection.controller.dto.UserCollectionDTO;
import com.mtgcollectionorganizer.api.user.collection.service.UserCollectionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/collection")
@AllArgsConstructor
public class UserCollectionController {

    private final UserCollectionService userCollectionService;
    private final LoginService loginService;

    @GetMapping("/{id}/")
    @ResponseStatus(HttpStatus.OK)
    public UserCollectionDTO getUserCollectionById(@PathVariable("id") final String id) {
        return userCollectionService.getCollectionById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserCollectionDTO createUserCollection(
            @RequestBody final UserCollectionDTO userCollectionDTO,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        return userCollectionService.createCollection(
                        userCollectionDTO,
                        loginService.getUsernameByToken(authorizationHeader)
                );
    }

    @PutMapping("{collection_id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addCardToCollection(
            @RequestBody final String cardId,
            @PathVariable("collection_id") final String collectionId,
            @RequestHeader("Authorization") String authorizationHeader) {
        userCollectionService.addCardToCollection(
                collectionId,
                cardId,
                loginService.getUsernameByToken(authorizationHeader));
    }
}
