package ru.soyuz_kom.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class MessageDTO {

    @NotNull
    @Size(min=5, max=500)
    public String message;
    public boolean isSms;
    public boolean isEmail;
    public String[] emails;
}
