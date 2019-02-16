package ru.soyuz_kom.dto.smotreshka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter @Setter @AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountListDTO {

    public List<AccountDTO> accounts;

    public AccountListDTO() {
        accounts = new ArrayList<AccountDTO>();
    }
}
