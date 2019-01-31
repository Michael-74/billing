package ru.soyuz_kom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import ru.soyuz_kom.validator.UniqueName;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "smotreshkas")
@ToString(of = {"id"})
@EqualsAndHashCode(callSuper = false)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Smotreshka extends Datetime {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "url")
    @Size(min=2, max=255)
    @NotNull
    private String url;

    @NotNull
    @Column(name = "login")
    private String login;

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
