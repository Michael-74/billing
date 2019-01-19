package ru.soyuz_kom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import ru.soyuz_kom.entity.view.Views;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "tvs")
@ToString(of = {"id", "name"})
@EqualsAndHashCode(callSuper = false)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Tv extends Datetime {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonView(Views.ClientsAndServicesIdName.class)
    private Integer id;

    @Column(name = "name")
    @Size(min=2, max=50)
    @NotNull
    private String name;

    @Column(name = "is_status")
    private Boolean isStatus;

    @JsonView(Views.ClientsAndServicesIdName.class)
    public String getVal () {
        return this.name;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @BatchSize(size=25)
    @JoinTable(name = "tvs_tasks",
            joinColumns = @JoinColumn(name = "id_tv"),
            inverseJoinColumns = @JoinColumn(name = "id_task") )
    private Set<Task> tasks;

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "tvs")
    private Set<Client> clients;

    @PrePersist
    void preInsert() {
        if (this.isStatus == null)
            this.isStatus = false;
    }

    @PreRemove
    private void preRemove() {
        //clients.forEach( client -> client.);
    }
}
