package ru.soyuz_kom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import ru.soyuz_kom.entity.enums.TypeDiscountEnum;
import ru.soyuz_kom.validator.UniqueContractClient;
import ru.soyuz_kom.validator.UniqueIpClient;
import ru.soyuz_kom.validator.UniqueLoginClient;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "mikrotik_datas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MikrotikData extends Datetime {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_mikrotik")
    @NotNull
    @Size(min=1, max=50)
    private String mikrotikId;

    @Column(name = "id_mikrotik_setting")
    @NotNull
    private Integer mikrotikSettingId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_client", referencedColumnName = "id")
    @NotNull
    private Client clientId;

    /*
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional=false)
    @JoinColumn(name = "id_client", insertable=false, updatable=false)
    private Client client;
    */
}
