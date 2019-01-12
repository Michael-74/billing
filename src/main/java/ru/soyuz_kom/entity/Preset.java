package ru.soyuz_kom.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "presets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Preset {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Size(min=2, max=50)
    private String url;
    @NotNull
    @Size(min=2, max=20)
    @Column(unique = true, name = "name")
    private String name;
    @NotNull
    @Column(name = "settings", columnDefinition = "TEXT")
    private String settings;

}
