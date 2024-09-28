package com.distribuidos.users.services;

import com.distribuidos.users.entities.UserEntity;
import com.distribuidos.users.repositories.UserRepository;
import com.distribuidos.users.services.facades.centarlizer.CentralizerFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CentralizerFacade centralizerFacade;

    public Mono<UserEntity> getUserByDocumentId(Long documentId) {

        log.info("Returning user " + documentId.toString());
        return userRepository.findByDocumentId(documentId)
                .defaultIfEmpty(UserEntity.builder().build());
    }

    public Mono<UserEntity> createUser(UserEntity user) {

        log.info("Creating user " + user.getDocumentId());

        return userRepository.save(user)
                .map(createdUser -> createdUser);
    }

    public Mono<Boolean> deleteUser(Long documentId) {

        log.info("Deleting user " + documentId.toString());

        return centralizerFacade.unregisterCitizen(documentId)
                .flatMap(confirmationResponse -> userRepository.deleteById(documentId)
                        .map(ignore -> true));
    }

}
