package ru.soyuz_kom.dto;

import lombok.Getter;
import lombok.Setter;
import ru.soyuz_kom.entity.Client;
import ru.soyuz_kom.entity.Internet;
import ru.soyuz_kom.entity.view.Views;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ClientDTO {
    public Integer id;
    private String fio;
    private String login;
    private String balance;
    private String contract;
    private String ip;
    private String address;
    private String phone;
    private String email;

    private Internet internet;

    private Integer loyalty;
    private Integer discount;

    //private TypeDiscountEnum typeDiscount;

    private Boolean isStatus;
    private Boolean isPromisedPay;
    private String note;

    public List<ClientDTO> getAllDTOList(List<Client> clientList){
        List<ClientDTO> clientDTOList = new ArrayList();
        for(Client client: clientList) {
            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setId(client.getId());
            clientDTO.setLogin(client.getLogin());
            clientDTO.setFio(client.getFio());
            clientDTO.setInternet(client.getInternet());

            clientDTOList.add(clientDTO);
        }

        return clientDTOList;
    }
}
