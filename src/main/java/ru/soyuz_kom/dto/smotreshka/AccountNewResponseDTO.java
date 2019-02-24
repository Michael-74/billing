package ru.soyuz_kom.dto.smotreshka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountNewResponseDTO {

    public String id;
    public boolean created;
}
