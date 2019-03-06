package ru.soyuz_kom.dto.smotreshka;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientSmotreshkaUpdateDTO {
    public Integer smotreshkaSettingId;
    public String id;
    public String login;
    public String email;
    public String password;
    public List purchases;
}
