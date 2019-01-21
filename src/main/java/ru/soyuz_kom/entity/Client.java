package ru.soyuz_kom.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import ru.soyuz_kom.entity.enums.TypeDiscountEnum;
import ru.soyuz_kom.entity.view.Views;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client extends Datetime implements Serializable {

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
    @Size(min=1, max=20)
    private String ip;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
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
