package ru.soyuz_kom.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tvs")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Tv {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_lifestream")
    private Integer lifestreamId;

    @Column(name = "name")
    private String name;
}
