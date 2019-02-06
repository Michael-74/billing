package ru.soyuz_kom.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {

    public String message;
    public boolean isSms;
    public boolean isEmail;
    public String[] emails;
}
