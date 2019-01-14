package ru.soyuz_kom.dto;

import lombok.Getter;
import lombok.Setter;
import ru.soyuz_kom.entity.Internet;
import ru.soyuz_kom.entity.Rent;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RentDTO {
    public Integer id;
    private String name;
    private String description;
    private Boolean status;
    private String val;

    public List<RentDTO> setRentDTOList(List<Rent> rentList){
        List<RentDTO> rentDTOList = new ArrayList();
        for(Rent rent: rentList) {
            RentDTO internetDTO = new RentDTO();
            internetDTO.setId(rent.getId());
            internetDTO.setName(rent.getName());
            internetDTO.setDescription(rent.getDescription());
            internetDTO.setStatus(rent.getStatus());
            internetDTO.setVal(rent.getName());

            rentDTOList.add(internetDTO);
        }

        return rentDTOList;
    }
}
