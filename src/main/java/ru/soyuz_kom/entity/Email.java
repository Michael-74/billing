package ru.soyuz_kom.entity;

import lombok.*;
import ru.soyuz_kom.entity.enums.TypeDiscountEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "emails")
@ToString(of = {"id"})
@EqualsAndHashCode(callSuper = false)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Email extends Datetime {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "host")
    @Size(min=2, max=255)
    @NotNull
    private String host;

    @Column(name = "port")
    @Size(min=2, max=10)
    @NotNull
    private String port;

    @NotNull
    @Column(name = "login")
    private String login;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_encription", columnDefinition="enum('ssl','web')")
    private TypeEncriptionEnum typeEncription;

    @NotNull
    @Column(name = "password")
    private String password;

    @Column(name = "is_status")
    private Boolean isStatus;

    @PrePersist
    void preInsert() {
        if (this.isStatus == null)
            this.isStatus = false;
    }
}
