package ru.soyuz_kom.provider;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.soyuz_kom.dto.smotreshka.AccountDTO;
import ru.soyuz_kom.dto.smotreshka.AccountInfoDTO;
import ru.soyuz_kom.dto.smotreshka.AccountListDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Getter @Setter
public class SmotreshkaProvider {

    @Autowired
    private RestTemplate restTemplate;

    private String url;
    private String login;
    private String password;

    public SmotreshkaProvider instance(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;

        return this;
    }

    /**
     * Получаем всех абонентов со всеми их данными
     */
    public List getAccounts() {
        String str = "/v2/accounts";

        AccountListDTO response = restTemplate.getForObject(this.url + str, AccountListDTO.class);
        List<AccountDTO> accounts = response.getAccounts();

        return accounts;
    }

    /**
     * Получаем одного абонента со всеми его данными
     * @param id
     * @return
     */
    public AccountDTO getAccount(String id) {
        String str = "/v2/accounts/{id}";

        return restTemplate.getForObject(this.url + str, AccountDTO.class, id);
    }

    public void setAccountInfo() {
        setAccountInfo(null, null, null, null, null);
    }

    public void setAccountInfo(String id, String date, String address, String fio, String period) {
        String str = "/v2/accounts/" + id + "/update";
        Map info = new HashMap<String, String>();
        AccountInfoDTO infoData = new AccountInfoDTO();
        infoData.setActivation_date(date);
        infoData.setAddress(address);
        infoData.setFio(fio);
        infoData.setPeriod(period);


        info.put("info", infoData);

        Object obj = restTemplate.postForObject(this.url + str, info, Object.class);
        System.out.println(obj);
    }
}
