package ru.soyuz_kom.dto.smotreshka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountInfoDTO {

    public String activation_date = null;
    public String address = null;
    public String period = null;
    public String fio = null;

}
