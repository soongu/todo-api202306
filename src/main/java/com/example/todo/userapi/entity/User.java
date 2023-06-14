package com.example.todo.userapi.entity;

import com.example.todo.todoapi.entity.Todo;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id; // 계정명이 아니라 식별코드

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userName;

    @CreationTimestamp
    private LocalDateTime joinDate;


    @Enumerated(EnumType.STRING)
//    @ColumnDefault("'COMMON'")
    @Builder.Default
    private Role role = Role.COMMON; // 유저 권한


    // 등급 수정 메서드
    public void changeRole(Role role) {
        this.role = role;
    }

}
