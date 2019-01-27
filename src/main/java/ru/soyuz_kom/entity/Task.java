package ru.soyuz_kom.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import ru.soyuz_kom.entity.enums.TypeWriteOffEnum;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tasks")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Task extends Datetime {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Size(min=2, max=50)
    @NotNull
    private String name;

    @Column(name = "price")
    @Digits(integer=10, fraction=2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_write_off", columnDefinition="ENUM('onetime','daily','monthly')", nullable = false)
    private TypeWriteOffEnum typeWriteOff;

    @Column(name = "datetime")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @NotNull
    private Date datetime;

    @Column(name = "day_in_month")
    private Integer dayInMonth = 0;

    @Column(name = "day_start")
    private Integer dayStart;

    @Column(name = "month_start")
    private Integer monthStart;

    @Column(name = "day_end")
    private Integer dayEnd;

    @Column(name = "month_end")
    private Integer monthEnd;

    @Column(name = "is_write_off_rent")
    private Boolean isWriteOffRent = false;

    @Column(name = "is_installments")
    private Boolean isInstallments = false;

    @Column(name = "price_installments")
    @Digits(integer=10, fraction=2)
    private BigDecimal priceInstallments;

    @ManyToMany(mappedBy = "tasks", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Rent> rents;

    public Set<Rent> getRents() {
        return rents;
    }

    public void setRents(Set<Rent> rents) {
        this.rents = rents;
    }

    @ManyToMany(mappedBy = "tasks", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Tv> tvs;

    public Set<Tv> getTvs() {
        return tvs;
    }

    public void setTvs(Set<Tv> tvs) {
        this.tvs = tvs;
    }

    @ManyToMany(mappedBy = "tasks", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Internet> internets;

    public Set<Internet> getInternets() {
        return internets;
    }

    public void setInternets(Set<Internet> internets) {
        this.internets = internets;
    }

    @PrePersist
    void preInsert() {
        if (this.price == null)
            this.price = BigDecimal.valueOf(0);
        if (this.priceInstallments == null)
            this.priceInstallments = BigDecimal.valueOf(0);
        if (this.dayInMonth == null || this.dayInMonth == 0)
            this.dayInMonth = 0;
    }

}
