package ru.soyuz_kom.dto;

import lombok.Getter;
import lombok.Setter;
import ru.soyuz_kom.entity.Client;
import ru.soyuz_kom.entity.Internet;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class InternetDTO {
    public Integer id;
    private String name;
    private String speed;
    private Boolean status;

    public List<InternetDTO> getAllInternetDTOList(List<Internet> internetList){
        List<InternetDTO> internetDTOList = new ArrayList();
        for(Internet internet: internetList) {
            InternetDTO internetDTO = new InternetDTO();
            internetDTO.setId(internet.getId());
            internetDTO.setName(internet.getName());
            internetDTO.setSpeed(internet.getSpeed());
            internetDTO.setStatus(internet.getStatus());
            internetDTOList.add(internetDTO);
        }

        return internetDTOList;
    }
}
