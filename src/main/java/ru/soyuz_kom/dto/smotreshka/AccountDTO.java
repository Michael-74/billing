package ru.soyuz_kom.dto.smotreshka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDTO {

    public String id;
    public AccountInfoDTO info;
    public String city;
    public String email;
    public String username;
    public String created;
    public String updated;
    public String is_blocked;
    public List<AccountSubscriptionsDTO> subscriptions = new ArrayList<AccountSubscriptionsDTO>();
}
