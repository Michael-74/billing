package ru.soyuz_kom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import ru.soyuz_kom.entity.view.Views;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Set;

@Entity
@Table(name = "internets")
@ToString(of = {"id", "name"})
@EqualsAndHashCode(callSuper = false)
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

    @ManyToMany(fetch = FetchType.LAZY)
    @BatchSize(size=25)
    @JoinTable(name = "internets_tasks",
            joinColumns = @JoinColumn(name = "id_internet"),
            inverseJoinColumns = @JoinColumn(name = "id_task") )
    private Set<Task> tasks;

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    /*
    @JsonIgnore
    @OneToMany(mappedBy = "internet")
    private Set<Client> clients;

    public Set<Client> getClient() {
        return clients;
    }

    public void setClient(Set<Client> clients) {
        this.clients = clients;
    }
*/
/*
    Задает дефолтные данные
    @PrePersist
    public void prePersist() {
        if(status == null)
            status = 0;
    }
    */
}
