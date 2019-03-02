package ru.soyuz_kom.entity;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import ru.soyuz_kom.entity.enums.TypeDiscountEnum;
import ru.soyuz_kom.entity.listener.ClientListener;
import ru.soyuz_kom.validator.UniqueContractClient;
import ru.soyuz_kom.validator.UniqueIpClient;
import ru.soyuz_kom.validator.UniqueLoginClient;

import javax.persistence.*;
import javax.validation.constraints.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@EntityListeners(ClientListener.class)
@Table(name = "clients")
@UniqueLoginClient("login")
@UniqueContractClient
@UniqueIpClient
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client extends Datetime {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fio")
    @NotNull
    @Size(min=2, max=50)
    private String fio;

    @Column(name = "login")
    @NotNull
    @Size(min=2, max=20)
    private String login;

    @Column(name = "balance")
    @Digits(integer=10, fraction=2)
    private BigDecimal balance;

    @Column(name = "contract")
    @NotNull
    @Size(min=1, max=50)
    private String contract;

    @Column(name = "ip")
    @NotNull
    @Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$", message="неправильно задан")
    @Size(min=1, max=20)
    private String ip;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    @Email
    private String email;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "id_internet", referencedColumnName = "id")
    private Internet internet;

    @Column(name = "loyalty")
    private Integer loyalty;

    @Column(name = "discount")
    private Integer discount = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_discount", columnDefinition="enum('discount10','discount20')")
    private TypeDiscountEnum typeDiscount;

    @Column(name = "is_status")
    private Boolean isStatus;

    @Column(name = "is_promised_pay")
    private Boolean isPromisedPay;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @OneToMany(mappedBy = "clientId", fetch = FetchType.EAGER, cascade={CascadeType.ALL})
    private Set<MikrotikData> mikrotikDatas;

    public Set<MikrotikData> getMikrotikDatas() {
        return mikrotikDatas;
    }

    public void setMikrotikDatas(Set<MikrotikData> mikrotikDatas) {
        this.mikrotikDatas = mikrotikDatas;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @BatchSize(size=25)
    @JoinTable(name = "clients_tvs",
            joinColumns = @JoinColumn(name = "id_client"),
            inverseJoinColumns = @JoinColumn(name = "id_tv") )
    private Set<Tv> tvs;

    public Set<Tv> getTvs() {
        return tvs;
    }

    public void setTvs(Set<Tv> tvs) {
        this.tvs = tvs;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @BatchSize(size=25)
    @JoinTable(name = "clients_rents",
            joinColumns = @JoinColumn(name = "id_client"),
            inverseJoinColumns = @JoinColumn(name = "id_rent") )
    private Set<Rent> rents;

    public Set<Rent> getRents() {
        return rents;
    }

    public void setRents(Set<Rent> rents) {
        this.rents = rents;
    }

    @PrePersist
    void preInsert() {
        if (this.isStatus == null)
            this.isStatus = false;
        if (this.isPromisedPay == null)
            this.isPromisedPay = false;
        if (this.balance == null)
            this.balance = BigDecimal.valueOf(0);
    }
}
