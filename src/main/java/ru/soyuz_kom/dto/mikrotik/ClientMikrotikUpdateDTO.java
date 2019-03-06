package ru.soyuz_kom.dto.mikrotik;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.soyuz_kom.entity.*;
import ru.soyuz_kom.entity.enums.TypeDiscountEnum;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
public class ClientMikrotikUpdateDTO {
    public Integer mikrotikSettingId;
    public String number;
    public String ip;
    public String list;
    public String comment;
}
