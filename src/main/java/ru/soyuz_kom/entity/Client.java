package ru.soyuz_kom.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import ru.soyuz_kom.entity.enums.TypeDiscountEnum;
import ru.soyuz_kom.entity.view.Views;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "clients")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Client extends Datetime {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonView(Views.ClientsAndServicesIdName.class)
    private Integer id;

    @Column(name = "fio")
    @NotNull
    @Size(min=2, max=50)
    @JsonView(Views.ClientsAndServicesIdName.class)
    private String fio;

    @Column(name = "login")
    @NotNull
    @Size(min=2, max=20)
    @JsonView(Views.ClientsAndServicesIdName.class)
    private String login;

    @Column(name = "balance")
    @JsonView(Views.ClientsAndServicesIdName.class)
    private String balance;

    @Column(name = "contract")
    @NotNull
    @Size(min=1, max=50)
    @JsonView(Views.ClientsAndServicesIdName.class)
    private String contract;

    @Column(name = "ip")
    @NotNull
    @Size(min=1, max=20)
    @JsonView(Views.ClientsAndServicesIdName.class)
    private String ip;

    @Column(name = "address")
    @JsonView(Views.ClientsAndServicesIdName.class)
    private String address;

    @Column(name = "phone")
    @JsonView(Views.ClientsAndServicesIdName.class)
    private String phone;

    @Column(name = "email")
    @JsonView(Views.ClientsAndServicesIdName.class)
    private String email;

    @JsonView(Views.ClientsAndServicesIdName.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_internet", referencedColumnName = "id")
    private Internet internet;

    @Column(name = "loyalty")
    @JsonView(Views.ClientsAndServicesIdName.class)
    private Integer loyalty;

    @Column(name = "discount")
    @JsonView(Views.ClientsAndServicesIdName.class)
    private Integer discount = 0;

    @Enumerated(EnumType.STRING)
    @JsonView(Views.ClientsAndServicesIdName.class)
    @Column(name = "type_discount", columnDefinition="ENUM('discount10','discount20')", nullable = false)
    private TypeDiscountEnum typeDiscount;

    @JsonView(Views.ClientsAndServicesIdName.class)
    @Column(name = "is_status")
    private Boolean isStatus;

    @JsonView(Views.ClientsAndServicesIdName.class)
    @Column(name = "is_promised_pay")
    private Boolean isPromisedPay;

    @JsonView(Views.ClientsAndServicesIdName.class)
    @Column(name = "note", columnDefinition = "TEXT")
    private String note;
}
