package ru.soyuz_kom.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.soyuz_kom.entity.Client;
import ru.soyuz_kom.entity.Internet;
import ru.soyuz_kom.entity.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class InternetDTO {
    public Integer id;
    private String name;
    private Integer speed;
    private Boolean isStatus;
    private Set<Task> tasks;
    private String val;
    @JsonFormat(pattern="dd.MM.yyyy HH:mm")
    public Date createdAt;
    @JsonFormat(pattern="dd.MM.yyyy HH:mm")
    public Date updatedAt;

    public List<InternetDTO> setIternetDTOList(List<Internet> internetList){
        List<InternetDTO> internetDTOList = new ArrayList();
        for(Internet internet: internetList) {
            InternetDTO internetDTO = new InternetDTO();
            internetDTO.setId(internet.getId());
            internetDTO.setName(internet.getName());
            internetDTO.setSpeed(internet.getSpeed());
            internetDTO.setIsStatus(internet.getIsStatus());
            internetDTO.setTasks(internet.getTasks());
            internetDTO.setVal(internet.getName());
            internetDTO.setCreatedAt(internet.getCreatedAt());
            internetDTO.setUpdatedAt(internet.getUpdatedAt());

            internetDTOList.add(internetDTO);
        }

        return internetDTOList;
    }
}
