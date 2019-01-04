package ru.soyuz_kom.entity;

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
@Table(name = "tasks")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Size(min=2, max=50)
    @NotNull
    private String name;

    @Column(name = "price")
    private String price;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_write_off", columnDefinition="ENUM('onetime','daily','monthly')" ,nullable = false)
    private TypeWriteOffEnum typeWriteOff;

    @Column(name = "datetime")
    @Size(min=2, max=50)
    @NotNull
    private String datetime;

    @Column(name = "day_start")
    private Integer dayStart;

    @Column(name = "month_start")
    private Integer monthStart;

    @Column(name = "day_end")
    private Integer dayEnd;

    @Column(name = "month_end")
    private Integer monthEnd;

    @Column(name = "is_write_off_rent")
    private Boolean isWriteOffRent;

    @Column(name = "is_installments")
    private Boolean isInstallments;

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
