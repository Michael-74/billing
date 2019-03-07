package ru.soyuz_kom.dto.smotreshka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class SubscriptionDTO {

    public Integer id;
    public boolean valid;
}
