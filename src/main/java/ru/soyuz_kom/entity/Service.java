package ru.soyuz_kom.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.soyuz_kom.entity.enums.ServiceTypeEnum;

import javax.persistence.*;

@Entity
@Table(name = "services")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Service {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition="ENUM('internet','tv','rent')", nullable = false)
    private ServiceTypeEnum serviceTypeEnum;

    @Column(name = "status")
    private Integer status;
}
