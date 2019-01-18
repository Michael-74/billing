package ru.soyuz_kom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "rents")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Rent extends Datetime {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Size(min=2, max=50)
    @NotNull
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_status")
    private Boolean isStatus;

    public String getVal () {
        return this.name;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @BatchSize(size=25)
    @JoinTable(name = "rents_tasks",
            joinColumns = @JoinColumn(name = "id_rent"),
            inverseJoinColumns = @JoinColumn(name = "id_task") )
    private Set<Task> tasks;

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
    }
}
