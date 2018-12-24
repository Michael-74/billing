package ru.soyuz_kom.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "tasks")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Tasks {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private String price;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_write_off", columnDefinition="ENUM('onetime','daily','monthly')" ,nullable = false)
    private TypeWriteOffEnum typeWriteOff;

    @Column(name = "datetime")
    private String datetime;

    @Column(name = "day_before_start")
    private Integer dayBeforeStart;

    @Column(name = "month_before_start")
    private Integer monthBeforeStart;

    @Column(name = "day_before_end")
    private Integer dayBeforeEnd;

    @Column(name = "month_before_end")
    private Integer monthBeforeEnd;

    @Column(name = "is_write_off_rent")
    private Integer isWriteOffRent;

    @Column(name = "is_installments")
    private Integer isInstallments;

    @Column(name = "price_installments")
    private String priceInstallments;

    @Basic(optional = false)
    @Column(name = "created_at", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Basic(optional = false)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public String getDatetime() {
        return datetime;
    }
}
