package br.com.juniorbarata.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PutMapping;
@Data //Getters e Settes automacos // @Gettes @Settes ou @Data para os dois
@Entity(name="tb_users")
public class UserModel {
    ///Chave Primaria
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique=true)
    private String username;
    private String name;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;


}

