package ru.soyuz_kom.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import ru.soyuz_kom.entity.view.Views;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Entity
@Table(name = "internets")
@ToString(of = {"id", "name"})
@EqualsAndHashCode(of = {"id"})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Internet extends Datetime {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonView(Views.ClientsAndServicesIdName.class)
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

    @JsonView(Views.ClientsAndServicesIdName.class)
    public String getVal () {
        return this.name;
    }
    /*
    Задает дефолтные данные
    @PrePersist
    public void prePersist() {
        if(status == null)
            status = 0;
    }
    */
}
