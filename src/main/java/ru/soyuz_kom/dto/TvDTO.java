package ru.soyuz_kom.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.soyuz_kom.entity.Tv;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TvDTO {
    public Integer id;
    private String name;
    private Boolean status;
    private String val;
    @JsonFormat(pattern="dd.MM.yyyy HH:mm")
    public Date createdAt;
    @JsonFormat(pattern="dd.MM.yyyy HH:mm")
    public Date updatedAt;

    public List<TvDTO> setTvDTOList(List<Tv> tvList){
        List<TvDTO> tvDTOList = new ArrayList();
        for(Tv tv: tvList) {
            TvDTO tvDTO = new TvDTO();
            tvDTO.setId(tv.getId());
            tvDTO.setName(tv.getName());
            tvDTO.setStatus(tv.getIsStatus());
            tvDTO.setVal(tv.getName());
            tvDTO.setCreatedAt(tv.getCreatedAt());
            tvDTO.setUpdatedAt(tv.getUpdatedAt());

            tvDTOList.add(tvDTO);
        }

        return tvDTOList;
    }
}
