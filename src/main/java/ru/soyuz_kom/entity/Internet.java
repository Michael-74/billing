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
@Table(name = "internets")
@UniqueName
@ToString(of = {"id", "name"})
@EqualsAndHashCode(callSuper = false)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Internet extends Datetime {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Size(min=2, max=50)
    @NotNull
    private String name;

    @NotNull
    @Column(name = "speed")
    private Integer speed;

    @Column(name = "is_status")
    private Boolean isStatus;

    public String getVal () {
        return this.name;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @BatchSize(size=25)
    @JoinTable(name = "internets_tasks",
            joinColumns = @JoinColumn(name = "id_internet"),
            inverseJoinColumns = @JoinColumn(name = "id_task") )
    private Set<Task> tasks;

    @JsonIgnore
    @OneToMany(mappedBy = "internet", fetch = FetchType.EAGER, cascade={CascadeType.DETACH})
    private Set<Client> clients;

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    @PrePersist
    void preInsert() {
        if (this.isStatus == null)
            this.isStatus = false;
        if (this.speed == null)
            this.speed = 0;
    }

    @PreRemove
    private void preRemove() {
        clients.forEach( client -> client.setInternet(null));
    }
}
