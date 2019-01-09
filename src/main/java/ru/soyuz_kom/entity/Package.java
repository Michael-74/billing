package ru.soyuz_kom.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.soyuz_kom.entity.enums.TypeWriteOffEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "packages")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Package {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Size(min=2, max=100)
    @NotNull
    private String name;

    @Column(name = "internet")
    private String internet;

    @Column(name = "tv")
    private String tv;

    @Column(name = "rent")
    private String rent;

    @Basic(optional = false)
    @Column(name = "created_at", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Basic(optional = false)
    @Column(name = "updated_at", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
