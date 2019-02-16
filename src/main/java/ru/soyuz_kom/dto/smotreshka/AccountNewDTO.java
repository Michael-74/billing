package ru.soyuz_kom.dto.smotreshka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountNewDTO {

    public String username;
    public String email;
    public String password;
    public List purchases;
    public AccountInfoDTO info;

    public AccountNewDTO(String username, String email, String password, List purchases, AccountInfoDTO info) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.purchases = purchases;
        this.info = info;
    }

    public AccountNewDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
