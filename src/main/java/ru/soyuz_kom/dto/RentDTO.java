package ru.soyuz_kom.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.soyuz_kom.entity.Internet;
import ru.soyuz_kom.entity.Rent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class RentDTO {
    public Integer id;
    private String name;
    private String description;
    private Boolean status;
    private String val;
    @JsonFormat(pattern="dd.MM.yyyy HH:mm")
    public Date createdAt;
    @JsonFormat(pattern="dd.MM.yyyy HH:mm")
    public Date updatedAt;

    public List<RentDTO> setRentDTOList(List<Rent> rentList){
        List<RentDTO> rentDTOList = new ArrayList();
        for(Rent rent: rentList) {
            RentDTO rentDTO = new RentDTO();
            rentDTO.setId(rent.getId());
            rentDTO.setName(rent.getName());
            rentDTO.setDescription(rent.getDescription());
            rentDTO.setStatus(rent.getIsStatus());
            rentDTO.setVal(rent.getName());
            rentDTO.setCreatedAt(rent.getCreatedAt());
            rentDTO.setUpdatedAt(rent.getUpdatedAt());

            rentDTOList.add(rentDTO);
        }

        return rentDTOList;
    }
}
