package ru.soyuz_kom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "smotreshka_datas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SmotreshkaData extends Datetime {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_smotreshka")
    @NotNull
    @Size(min=1, max=50)
    private String smotreshkaId;

    @Column(name = "id_smotreshka_setting")
    @NotNull
    private Integer smotreshkaSettingId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_client", referencedColumnName = "id")
    @NotNull
    private Client clientId;
}
