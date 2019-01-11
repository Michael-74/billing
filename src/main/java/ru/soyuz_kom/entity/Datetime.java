package ru.soyuz_kom.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.TimeZone;

@MappedSuperclass
@Getter
@Setter
abstract class Datetime {
    private static final String TIME_ZONE="Asia/Yekaterinburg";
    private static final String FORMAT_DATETIME="dd.MM.yyyy' 'HH:mm";

    @Basic(optional = false)
    @Column(name = "created_at", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMAT_DATETIME, timezone = TIME_ZONE)
    private Date createdAt;

    @Basic(optional = false)
    @Column(name = "updated_at", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMAT_DATETIME, timezone = TIME_ZONE)
    private Date updatedAt;
}
