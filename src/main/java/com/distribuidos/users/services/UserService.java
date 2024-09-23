package com.distribuidos.users.services;

import com.distribuidos.users.entities.UserEntity;
import com.distribuidos.users.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Mono<UserEntity> getUserByDocumentId(Long documentId) {

        return userRepository.findByDocumentId(documentId)
                .defaultIfEmpty(UserEntity.builder().build());
    }

    public Mono<Boolean> createUser(UserEntity user) {
        return userRepository.save(user)
                .map(createdUser -> true);
    }

}
