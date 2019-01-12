package ru.soyuz_kom.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import ru.soyuz_kom.entity.view.Views;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "clients")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonView(Views.ClientsAndServicesIdName.class)
    private Integer id;
    @NotNull
    @Size(min=2, max=50)
    @JsonView(Views.ClientsAndServicesIdName.class)
    private String fio;
    @NotNull
    @Size(min=2, max=20)
    @JsonView(Views.ClientsAndServicesIdName.class)
    private String login;
    @JsonView(Views.ClientsAndServicesIdName.class)
    private String balance;
    @NotNull
    @Size(min=1, max=50)
    @JsonView(Views.ClientsAndServicesIdName.class)
    private String contract;
    @NotNull
    @Size(min=1, max=20)
    @JsonView(Views.ClientsAndServicesIdName.class)
    private String ip;
    @JsonView(Views.ClientsAndServicesIdName.class)
    private String address;
    @JsonView(Views.ClientsAndServicesIdName.class)
    private String phone;
    @JsonView(Views.ClientsAndServicesIdName.class)
    private String email;
}
