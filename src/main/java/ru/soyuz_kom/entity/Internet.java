package ru.soyuz_kom.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Entity
@Table(name = "internets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Internet {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Size(min=2, max=50)
    @NotNull
    private String name;

    @Size(min=2, max=50)
    @NotNull
    @Column(name = "speed")
    private String speed;

    @Column(name = "status")
    private Boolean status;

    /*
    Задает дефолтные данные
    @PrePersist
    public void prePersist() {
        if(status == null)
            status = 0;
    }
    */
}
