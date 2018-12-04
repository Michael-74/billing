package ru.soyuz_kom.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "clients")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Size(min=2, max=50)
    private String fio;
    @NotNull
    @Size(min=2, max=20)
    private String login;
    private String balance;
    @NotNull
    @Size(min=1, max=50)
    private String contract;
    @NotNull
    @Size(min=1, max=20)
    private String ip;
    private String address;
    private String phone;
    private String email;
}
