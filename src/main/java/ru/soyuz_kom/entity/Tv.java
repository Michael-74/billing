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
@UniqueName
@Table(name = "tvs")
@ToString(of = {"id", "name"})
@EqualsAndHashCode(callSuper = false)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Tv extends Datetime {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Size(min=2, max=50)
    @NotNull
    private String name;

    @Column(name = "is_status")
    private Boolean isStatus;

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
    @ManyToMany(mappedBy = "tvs", fetch = FetchType.EAGER)
    private Set<Client> clients;

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

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
