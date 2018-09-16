package ru.soyuz_kom.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String fio;
    private String password;
    private String email;

    public User(String fio, String password, String email) {
        this.fio = fio;
        this.password = password;
        this.email = email;
    }
}
