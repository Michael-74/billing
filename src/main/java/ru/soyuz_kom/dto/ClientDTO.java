package ru.soyuz_kom.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.soyuz_kom.entity.*;
import ru.soyuz_kom.entity.enums.TypeDiscountEnum;
import ru.soyuz_kom.entity.view.Views;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Getter
@Setter
public class ClientDTO {
    public Integer id;
    public String fio;
    public String login;
    public BigDecimal balance;
    public String contract;
    public String ip;
    public String address;
    public String phone;
    public String email;
    public Internet internet;
    public Integer loyalty;
    public Integer discount;
    public TypeDiscountEnum typeDiscount;
    public Boolean isStatus;
    public Boolean isPromisedPay;
    public Set<MikrotikData> mikrotikDatas;
    public String note;
    public Set<Tv> tvs = new HashSet<>();
    public Set<Rent> rents = new HashSet<>();
    @JsonFormat(pattern="dd.MM.yyyy HH:mm")
    public Date createdAt;
    @JsonFormat(pattern="dd.MM.yyyy HH:mm")
    public Date updatedAt;

    public List<ClientDTO> setClientDTOList(List<Client> clientList){
        List<ClientDTO> clientDTOList = new ArrayList();
        for(Client client: clientList) {
            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setId(client.getId());
            clientDTO.setFio(client.getFio());
            clientDTO.setLogin(client.getLogin());
            clientDTO.setBalance(client.getBalance());
            clientDTO.setContract(client.getContract());
            clientDTO.setIp(client.getIp());
            clientDTO.setAddress(client.getAddress());
            clientDTO.setPhone(client.getPhone());
            clientDTO.setEmail(client.getEmail());
            clientDTO.setInternet(client.getInternet());
            clientDTO.setTvs(client.getTvs());
            clientDTO.setRents(client.getRents());

            clientDTO.setLoyalty(client.getLoyalty());
            clientDTO.setDiscount(client.getDiscount());
            clientDTO.setTypeDiscount(client.getTypeDiscount());
            clientDTO.setIsStatus(client.getIsStatus());
            clientDTO.setIsPromisedPay(client.getIsPromisedPay());
            clientDTO.setNote(client.getNote());
            clientDTO.setMikrotikDatas(client.getMikrotikDatas());
            clientDTO.setCreatedAt(client.getCreatedAt());
            clientDTO.setUpdatedAt(client.getUpdatedAt());

            clientDTOList.add(clientDTO);
        }

        return clientDTOList;
    }
}
