package com.distribuidos.users.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder(toBuilder = true)
@Table(name = "users")
public class UserEntity {

    @Id
    @Column("document_id")
    Long documentId;

    @Column("full_name")
    String fullName;

    @Column("status")
    String status;

    @Column("email")
    String email;

    @Column("description")
    String description;

    @Column("password")
    String password;

    @Column("address")
    String address;

}
