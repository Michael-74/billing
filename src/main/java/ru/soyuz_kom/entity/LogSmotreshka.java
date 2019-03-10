package ru.soyuz_kom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import ru.soyuz_kom.validation.DataJsonConverter;
import ru.soyuz_kom.validator.UniqueName;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "log_smotreshkas")
@ToString(of = {"id"})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LogSmotreshka extends Datetime implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type_action")
    @NotNull
    private String typeAction;

    @Column(name = "url")
    @NotNull
    private String url;

    @Column(name = "id_user")
    @NotNull
    private Integer userId;

    @Column(name = "is_success")
    private Boolean isSuccess;

    @Column(name = "request", columnDefinition = "TEXT")
    @Convert(converter = DataJsonConverter.class)
    private Object request;

    @Column(name = "response", columnDefinition = "TEXT")
    @Convert(converter = DataJsonConverter.class)
    private Object response;

    @PrePersist
    void preInsert() {
        if (this.isSuccess == null)
            this.isSuccess = false;
        if (this.userId == null)
            this.userId = 0;
    }
}
