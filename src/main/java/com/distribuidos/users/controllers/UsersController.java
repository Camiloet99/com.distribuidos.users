package com.distribuidos.users.controllers;

import com.distribuidos.users.entities.UserEntity;
import com.distribuidos.users.models.ResponseBody;
import com.distribuidos.users.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final UserService service;

    @PostMapping()
    public Mono<ResponseEntity<ResponseBody<UserEntity>>> createUser(
            @RequestBody UserEntity user) {

        return service.createUser(user)
                .map(ControllerUtils::created);
    }

    @GetMapping("/{documentId}")
    public Mono<ResponseEntity<ResponseBody<UserEntity>>> getUserByDocumentId(
            @RequestBody Long documentId) {

        return service.getUserByDocumentId(documentId)
                .map(ControllerUtils::ok);
    }

    @DeleteMapping("/{documentId}")
    public Mono<ResponseEntity<ResponseBody<Boolean>>> deleteUserF(
            @RequestBody Long documentId) {

        return service.deleteUser(documentId)
                .map(ControllerUtils::ok);
    }

}
