package com.mtgcollectionorganizer.api.user.collection.controller;

import com.mtgcollectionorganizer.api.user.collection.controller.dto.UserCollectionDTO;
import com.mtgcollectionorganizer.api.user.collection.service.UserCollectionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collection")
@AllArgsConstructor
public class UserCollectionController {

    private final UserCollectionService userCollectionService;

    @GetMapping("/{id}/")
    public ResponseEntity<UserCollectionDTO> getUserCollectionById(@PathVariable("id") final String id){
        return ResponseEntity.ok(userCollectionService.getCollectionById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUserCollection(@RequestBody final UserCollectionDTO userCollectionDTO){
        userCollectionService.createCollection(userCollectionDTO);
    }
}
