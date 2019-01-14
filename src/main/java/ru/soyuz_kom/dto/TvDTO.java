package ru.soyuz_kom.dto;

import lombok.Getter;
import lombok.Setter;
import ru.soyuz_kom.entity.Tv;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TvDTO {
    public Integer id;
    private String name;
    private Boolean status;
    private String val;

    public List<TvDTO> setTvDTOList(List<Tv> tvList){
        List<TvDTO> tvDTOList = new ArrayList();
        for(Tv tv: tvList) {
            TvDTO internetDTO = new TvDTO();
            internetDTO.setId(tv.getId());
            internetDTO.setName(tv.getName());
            internetDTO.setStatus(tv.getStatus());
            internetDTO.setVal(tv.getName());
            tvDTOList.add(internetDTO);
        }

        return tvDTOList;
    }
}
