package com.distribuidos.users.repositories;

import com.distribuidos.users.entities.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<UserEntity, Long> {
    
    Mono<UserEntity> findByDocumentId(Long documentId);

}
